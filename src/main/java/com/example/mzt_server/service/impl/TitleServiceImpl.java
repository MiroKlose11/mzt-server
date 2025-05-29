package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.TitleDTO;
import com.example.mzt_server.entity.MemberTitle;
import com.example.mzt_server.entity.Title;
import com.example.mzt_server.mapper.MemberTitleMapper;
import com.example.mzt_server.mapper.TitleMapper;
import com.example.mzt_server.service.TitleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 头衔服务实现类
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements TitleService {
    
    @Autowired
    private MemberTitleMapper memberTitleMapper;
    
    @Override
    public List<TitleDTO> listTitles(String type) {
        // 构建查询条件
        LambdaQueryWrapper<Title> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(type)) {
            queryWrapper.eq(Title::getType, type);
        }
        queryWrapper.orderByDesc(Title::getSort).orderByAsc(Title::getId);
        
        List<Title> titles = list(queryWrapper);
        
        // 转换为DTO
        return titles.stream().map(title -> {
            TitleDTO dto = new TitleDTO();
            BeanUtils.copyProperties(title, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public TitleDTO getTitleById(Integer id) {
        Title title = getById(id);
        if (title == null) {
            return null;
        }
        
        TitleDTO dto = new TitleDTO();
        BeanUtils.copyProperties(title, dto);
        return dto;
    }
    
    @Override
    public boolean saveTitle(TitleDTO titleDTO) {
        Title title = new Title();
        BeanUtils.copyProperties(titleDTO, title);
        title.setCreatedAt(LocalDateTime.now());
        title.setUpdatedAt(LocalDateTime.now());
        
        // 设置默认排序值
        if (title.getSort() == null) {
            title.setSort(0);
        }
        
        return save(title);
    }
    
    @Override
    public boolean updateTitle(TitleDTO titleDTO) {
        Title title = getById(titleDTO.getId());
        if (title == null) {
            return false;
        }
        
        BeanUtils.copyProperties(titleDTO, title);
        title.setUpdatedAt(LocalDateTime.now());
        
        return updateById(title);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeTitle(Integer id) {
        // 删除头衔与成员的关联
        LambdaQueryWrapper<MemberTitle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MemberTitle::getTitleId, id);
        memberTitleMapper.delete(queryWrapper);
        
        // 删除头衔
        return removeById(id);
    }
} 