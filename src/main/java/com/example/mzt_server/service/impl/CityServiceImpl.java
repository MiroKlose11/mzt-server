package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.CityDTO;
import com.example.mzt_server.entity.City;
import com.example.mzt_server.mapper.CityMapper;
import com.example.mzt_server.service.CityService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 城市服务实现类
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
    @Override
    public String getCityNameWithSuffix(Integer cityId) {
        if (cityId == null) {
            return null;
        }
        
        City city = this.getById(cityId);
        if (city == null) {
            return null;
        }
        
        String suffix = "";
        switch (city.getLeveltype()) {
            case 1:
                suffix = "省";
                break;
            case 2:
                suffix = "市";
                break;
            case 3:
                suffix = "区";
                break;
            default:
                suffix = "";
        }
        
        return city.getShortname() + suffix;
    }
    
    @Override
    public List<CityDTO> getCityDTOList() {
        LambdaQueryWrapper<City> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(City::getStatus, "1"); // 只获取状态为启用的城市
        queryWrapper.orderByAsc(City::getId);  // 按ID排序
        List<City> cityList = this.list(queryWrapper);
        
        // 转换为DTO
        return cityList.stream().map(city -> {
            CityDTO dto = new CityDTO();
            BeanUtils.copyProperties(city, dto);
            return dto;
        }).collect(Collectors.toList());
    }
} 