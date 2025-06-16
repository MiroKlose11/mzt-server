package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.CityDTO;
import com.example.mzt_server.dto.CityTreeDTO;
import com.example.mzt_server.entity.City;

import java.util.List;

/**
 * 城市服务接口
 */
public interface CityService extends IService<City> {
    /**
     * 根据城市ID获取城市名称（带省市区后缀）
     * @param cityId 城市ID
     * @return 城市名称
     */
    String getCityNameWithSuffix(Integer cityId);
    
    /**
     * 获取所有城市DTO列表
     * @return 城市DTO列表
     */
    List<CityDTO> getCityDTOList();
    
    /**
     * 获取城市树形结构
     * @return 城市树形结构列表（省级）
     */
    List<CityTreeDTO> getCityTree();
} 