package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.PositionDTO;
import com.example.mzt_server.entity.Position;

import java.util.List;

/**
 * 岗位服务接口
 */
public interface PositionService extends IService<Position> {
    /**
     * 获取所有岗位列表
     * @return 岗位DTO列表
     */
    List<PositionDTO> listAll();
    
    /**
     * 根据ID获取岗位
     * @param id 岗位ID
     * @return 岗位DTO
     */
    PositionDTO getById(Integer id);
    
    /**
     * 保存岗位
     * @param dto 岗位DTO
     * @return 是否成功
     */
    boolean savePosition(PositionDTO dto);
    
    /**
     * 更新岗位
     * @param dto 岗位DTO
     * @return 是否成功
     */
    boolean updatePosition(PositionDTO dto);
    
    /**
     * 删除岗位
     * @param id 岗位ID
     * @return 是否成功
     */
    boolean removePosition(Integer id);
} 