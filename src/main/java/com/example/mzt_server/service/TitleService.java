package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.TitleDTO;
import com.example.mzt_server.entity.Title;

import java.util.List;

/**
 * 头衔服务接口
 */
public interface TitleService extends IService<Title> {
    
    /**
     * 获取头衔列表
     * @param type 类型（可选）
     * @return 头衔列表
     */
    List<TitleDTO> listTitles(String type);
    
    /**
     * 根据ID获取头衔详情
     * @param id 头衔ID
     * @return 头衔详情
     */
    TitleDTO getTitleById(Integer id);
    
    /**
     * 新增头衔
     * @param titleDTO 头衔信息
     * @return 是否成功
     */
    boolean saveTitle(TitleDTO titleDTO);
    
    /**
     * 更新头衔
     * @param titleDTO 头衔信息
     * @return 是否成功
     */
    boolean updateTitle(TitleDTO titleDTO);
    
    /**
     * 删除头衔
     * @param id 头衔ID
     * @return 是否成功
     */
    boolean removeTitle(Integer id);
} 