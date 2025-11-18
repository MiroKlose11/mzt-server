package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mzt_server.common.vo.SearchResult;
import com.example.mzt_server.dto.SearchRequest;
import com.example.mzt_server.dto.SearchResponse;
import com.example.mzt_server.entity.*;
import com.example.mzt_server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 搜索服务实现类
 */
@Service
public class SearchServiceImpl implements SearchService {
    
    @Autowired
    private ArticleService articleService;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private OrganizationService organizationService;
    
    @Autowired
    private StudentService studentService;
    
    /**
     * 支持的搜索类型列表
     */
    private static final List<String> SUPPORTED_TYPES = Arrays.asList(
            "article", "member", "course", "organization", "student"
    );
    
    @Override
    public SearchResponse search(SearchRequest request) {
        String keyword = request.getKeyword().trim();
        List<String> types = request.getTypes();
        
        // 如果没有指定类型，则搜索所有类型
        if (CollectionUtils.isEmpty(types)) {
            types = SUPPORTED_TYPES;
        } else {
            // 过滤出支持的类型
            types = types.stream()
                    .filter(SUPPORTED_TYPES::contains)
                    .collect(Collectors.toList());
        }
        
        // 如果过滤后没有有效类型，返回空结果
        if (CollectionUtils.isEmpty(types)) {
            return buildEmptyResponse(request);
        }
        
        // 合并所有搜索结果
        List<SearchResult> allResults = new ArrayList<>();
        long totalCount = 0;
        
        for (String type : types) {
            switch (type) {
                case "article":
                    List<SearchResult> articleResults = searchArticles(keyword, request);
                    allResults.addAll(articleResults);
                    totalCount += countArticles(keyword);
                    break;
                case "member":
                    List<SearchResult> memberResults = searchMembers(keyword, request);
                    allResults.addAll(memberResults);
                    totalCount += countMembers(keyword);
                    break;
                case "course":
                    List<SearchResult> courseResults = searchCourses(keyword, request);
                    allResults.addAll(courseResults);
                    totalCount += countCourses(keyword);
                    break;
                case "organization":
                    List<SearchResult> orgResults = searchOrganizations(keyword, request);
                    allResults.addAll(orgResults);
                    totalCount += countOrganizations(keyword);
                    break;
                case "student":
                    List<SearchResult> studentResults = searchStudents(keyword, request);
                    allResults.addAll(studentResults);
                    totalCount += countStudents(keyword);
                    break;
            }
        }
        
        // 分页处理
        int page = request.getPage();
        int size = request.getSize();
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, allResults.size());
        
        List<SearchResult> pagedResults = fromIndex < allResults.size() 
                ? allResults.subList(fromIndex, toIndex) 
                : new ArrayList<>();
        
        return SearchResponse.builder()
                .results(pagedResults)
                .total(totalCount)
                .page(page)
                .size(size)
                .totalPages((long) Math.ceil((double) totalCount / size))
                .build();
    }
    
    /**
     * 搜索文章
     */
    private List<SearchResult> searchArticles(String keyword, SearchRequest request) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Article::getTitle, keyword)
                .or().like(Article::getDescription, keyword));
        wrapper.eq(Article::getStatus, 2); // 只搜索已发布的文章
        wrapper.orderByDesc(Article::getUpdatetime);
        
        Page<Article> page = new Page<>(request.getPage(), request.getSize());
        Page<Article> result = articleService.page(page, wrapper);
        
        return result.getRecords().stream()
                .map(article -> SearchResult.builder()
                        .type("article")
                        .id(article.getId())
                        .title(article.getTitle())
                        .desc(article.getDescription())
                        .extra(entityToMap(article))
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 统计文章数量
     */
    private long countArticles(String keyword) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Article::getTitle, keyword)
                .or().like(Article::getDescription, keyword));
        wrapper.eq(Article::getStatus, 2);
        return articleService.count(wrapper);
    }
    
    /**
     * 搜索成员
     */
    private List<SearchResult> searchMembers(String keyword, SearchRequest request) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Member::getName, keyword)
                .or().like(Member::getIntroduction, keyword)
                .or().like(Member::getOrganization, keyword));
        wrapper.eq(Member::getStatus, 1); // 只搜索启用的成员
        wrapper.orderByDesc(Member::getWeight).orderByDesc(Member::getUpdatedAt);
        
        Page<Member> page = new Page<>(request.getPage(), request.getSize());
        Page<Member> result = memberService.page(page, wrapper);
        
        return result.getRecords().stream()
                .map(member -> SearchResult.builder()
                        .type("member")
                        .id(member.getId())
                        .title(member.getName())
                        .desc(member.getIntroduction())
                        .extra(entityToMap(member))
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 统计成员数量
     */
    private long countMembers(String keyword) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Member::getName, keyword)
                .or().like(Member::getIntroduction, keyword)
                .or().like(Member::getOrganization, keyword));
        wrapper.eq(Member::getStatus, 1);
        return memberService.count(wrapper);
    }
    
    /**
     * 搜索课程
     */
    private List<SearchResult> searchCourses(String keyword, SearchRequest request) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Course::getTitle, keyword)
                .or().like(Course::getDescription, keyword));
        wrapper.eq(Course::getStatus, true); // 只搜索启用的课程
        wrapper.orderByDesc(Course::getUpdatedAt);
        
        Page<Course> page = new Page<>(request.getPage(), request.getSize());
        Page<Course> result = courseService.page(page, wrapper);
        
        return result.getRecords().stream()
                .map(course -> SearchResult.builder()
                        .type("course")
                        .id(course.getId())
                        .title(course.getTitle())
                        .desc(course.getDescription())
                        .extra(entityToMap(course))
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 统计课程数量
     */
    private long countCourses(String keyword) {
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Course::getTitle, keyword)
                .or().like(Course::getDescription, keyword));
        wrapper.eq(Course::getStatus, true);
        return courseService.count(wrapper);
    }
    
    /**
     * 搜索机构
     */
    private List<SearchResult> searchOrganizations(String keyword, SearchRequest request) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Organization::getName, keyword)
                .or().like(Organization::getAddress, keyword));
        wrapper.eq(Organization::getStatus, 1); // 只搜索启用的机构
        wrapper.orderByDesc(Organization::getWeight).orderByDesc(Organization::getUpdatedAt);
        
        Page<Organization> page = new Page<>(request.getPage(), request.getSize());
        Page<Organization> result = organizationService.page(page, wrapper);
        
        return result.getRecords().stream()
                .map(org -> SearchResult.builder()
                        .type("organization")
                        .id(org.getId())
                        .title(org.getName())
                        .desc(org.getAddress())
                        .extra(entityToMap(org))
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 统计机构数量
     */
    private long countOrganizations(String keyword) {
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Organization::getName, keyword)
                .or().like(Organization::getAddress, keyword));
        wrapper.eq(Organization::getStatus, 1);
        return organizationService.count(wrapper);
    }
    
    /**
     * 搜索学生
     */
    private List<SearchResult> searchStudents(String keyword, SearchRequest request) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Student::getName, keyword)
                .or().like(Student::getSignature, keyword));
        wrapper.eq(Student::getStatus, 1); // 只搜索启用的学生
        wrapper.orderByDesc(Student::getUpdatedAt);
        
        Page<Student> page = new Page<>(request.getPage(), request.getSize());
        Page<Student> result = studentService.page(page, wrapper);
        
        return result.getRecords().stream()
                .map(student -> SearchResult.builder()
                        .type("student")
                        .id(student.getId())
                        .title(student.getName())
                        .desc(student.getSignature())
                        .extra(entityToMap(student))
                        .build())
                .collect(Collectors.toList());
    }
    
    /**
     * 统计学生数量
     */
    private long countStudents(String keyword) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.like(Student::getName, keyword)
                .or().like(Student::getSignature, keyword));
        wrapper.eq(Student::getStatus, 1);
        return studentService.count(wrapper);
    }
    
    /**
     * 将实体转换为Map（排除id字段）
     */
    private Map<String, Object> entityToMap(Object entity) {
        Map<String, Object> map = new HashMap<>();
        try {
            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();
            
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                
                // 排除id字段和serialVersionUID
                if (!"id".equals(fieldName) && !"serialVersionUID".equals(fieldName)) {
                    Object value = field.get(entity);
                    map.put(fieldName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 构建空响应
     */
    private SearchResponse buildEmptyResponse(SearchRequest request) {
        return SearchResponse.builder()
                .results(new ArrayList<>())
                .total(0L)
                .page(request.getPage())
                .size(request.getSize())
                .totalPages(0L)
                .build();
    }
}

