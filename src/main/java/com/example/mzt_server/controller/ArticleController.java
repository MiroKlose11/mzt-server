        package com.example.mzt_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mzt_server.common.Result;
import com.example.mzt_server.entity.Article;
import com.example.mzt_server.entity.ArticleContent;
import com.example.mzt_server.service.IArticleContentService;
import com.example.mzt_server.service.IArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 文章管理控制器
 * 提供文章的增删改查、分页、详情、内容等接口
 */
@Tag(name = "文章管理", description = "文章的增删改查接口")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArticleContentService articleContentService;

    /**
     * 新增文章
     * @param article 文章实体
     * @return 新增后的文章
     * 
     * 注意：authorId和authorType可选，不传时系统会设置默认值
     */
    @Operation(summary = "新增文章", description = "创建新文章，authorId和authorType可选，不传时系统会设置默认值")
    @PostMapping("")
    public Result<Article> add(@RequestBody Article article) {
        // 设置默认作者ID和作者类型，避免数据库非空约束报错
        if (article.getAuthorId() == null) {
            article.setAuthorId(1L); // 默认作者ID为1
        }
        if (article.getAuthorType() == null) {
            article.setAuthorType(0); // 默认作者类型为管理员
        }
        articleService.save(article);
        return Result.success(article);
    }

    /**
     * 修改文章
     * @param article 文章实体
     * @return 是否成功
     */
    @Operation(summary = "修改文章")
    @PutMapping("")
    public Result<Boolean> update(@RequestBody Article article) {
        boolean success = articleService.updateById(article);
        return Result.success(success);
    }

    /**
     * 删除文章
     * @param id 文章ID
     * @return 是否成功
     */
    @Operation(summary = "删除文章")
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean success = articleService.removeById(id);
        return Result.success(success);
    }

    /**
     * 文章分页
     * @param current 页码
     * @param size 每页条数
     * @param status 状态
     * @param channelId 栏目ID
     * @return 分页结果
     */
    @Operation(summary = "分页获取文章列表")
    @GetMapping("/page")
    public Result<Page<Article>> page(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页条数") @RequestParam(defaultValue = "10") Integer size,
            @Parameter(description = "状态") @RequestParam(required = false) Integer status,
            @Parameter(description = "栏目ID") @RequestParam(required = false) Long channelId) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        if (status != null) {
            queryWrapper.eq(Article::getStatus, status);
        }
        if (channelId != null) {
            queryWrapper.eq(Article::getChannelId, channelId);
        }
        queryWrapper.orderByDesc(Article::getId);
        articleService.page(page, queryWrapper);
        return Result.success(page);
    }

    /**
     * 文章详情
     * @param id 文章ID
     * @return 文章详情
     */
    @Operation(summary = "获取文章详情")
    @GetMapping("/{id}")
    public Result<Article> getById(@PathVariable Long id) {
        Article article = articleService.getById(id);
        return Result.success(article);
    }

    /**
     * 获取文章内容
     * @param id 文章ID
     * @return 文章内容
     */
    @Operation(summary = "获取文章内容")
    @GetMapping("/{id}/content")
    public Result<ArticleContent> getContent(@PathVariable Long id) {
        ArticleContent content = articleContentService.getById(id);
        return Result.success(content);
    }

    /**
     * 新增或修改文章内容
     * @param id 文章ID
     * @param content 文章内容实体
     * @return 是否成功
     */
    @Operation(summary = "保存文章内容")
    @PostMapping("/{id}/content")
    public Result<Boolean> saveContent(@PathVariable Long id, @RequestBody ArticleContent content) {
        content.setArticleId(id);
        boolean success = articleContentService.saveOrUpdate(content);
        return Result.success(success);
    }

    /**
     * 前台-获取已发布文章分页
     * @param current 页码
     * @param size 每页条数
     * @param channelId 栏目ID
     * @return 分页结果
     */
    @Operation(summary = "前台-分页获取已发布文章")
    @GetMapping("/frontend/page")
    public Result<Page<Article>> frontendPage(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long channelId) {
        Page<Article> page = new Page<>(current, size);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, 2); // 只查已发布
        if (channelId != null) {
            queryWrapper.eq(Article::getChannelId, channelId);
        }
        queryWrapper.orderByDesc(Article::getWeight).orderByDesc(Article::getPublishtime);
        articleService.page(page, queryWrapper);
        return Result.success(page);
    }

    /**
     * 前台-获取文章详情
     * @param id 文章ID
     * @return 文章详情（仅已发布）
     */
    @Operation(summary = "前台-获取文章详情")
    @GetMapping("/frontend/{id}")
    public Result<Article> frontendDetail(@PathVariable Long id) {
        Article article = articleService.getById(id);
        if (article == null || article.getStatus() != 2) {
            return Result.error("文章不存在或未发布");
        }
        return Result.success(article);
    }
} 