package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.CityDTO;
import com.example.mzt_server.dto.CityTreeDTO;
import com.example.mzt_server.entity.City;
import com.example.mzt_server.mapper.CityMapper;
import com.example.mzt_server.service.CityService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    public String getCityNameByLevel(Integer cityId) {
        if (cityId == null) {
            return null;
        }
        
        City city = this.getById(cityId);
        if (city == null) {
            return null;
        }
        
        // 如果是三级城市（区县），查找其父级（市级）
        if (city.getLeveltype() == 3) {
            City parentCity = this.getById(city.getParentid());
            if (parentCity != null) {
                return parentCity.getName();
            }
        }
        
        // 如果是一级（省）或二级（市），直接返回名称
        return city.getName();
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
    
    @Override
    public List<CityTreeDTO> getCityTree() {
        // 获取所有状态为启用的城市
        LambdaQueryWrapper<City> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(City::getStatus, "1");
        queryWrapper.orderByAsc(City::getId);
        List<City> allCities = this.list(queryWrapper);
        
        // 按级别和父ID分组
        Map<Integer, List<City>> levelTypeCities = allCities.stream()
                .collect(Collectors.groupingBy(City::getLeveltype));
        
        // 获取省级城市（leveltype=1）
        List<City> provinces = levelTypeCities.getOrDefault(1, new ArrayList<>());
        
        // 构建树形结构
        List<CityTreeDTO> result = new ArrayList<>();
        for (City province : provinces) {
            CityTreeDTO provinceNode = new CityTreeDTO();
            provinceNode.setId(province.getId());
            provinceNode.setName(province.getName());
            
            // 获取该省下的城市（parentid=省ID）
            List<City> cities = allCities.stream()
                    .filter(city -> city.getLeveltype() == 2 && city.getParentid().equals(province.getId()))
                    .collect(Collectors.toList());
            
            List<CityTreeDTO> cityNodes = new ArrayList<>();
            for (City city : cities) {
                CityTreeDTO cityNode = new CityTreeDTO();
                cityNode.setId(city.getId());
                cityNode.setName(city.getName());
                
                // 获取该市下的区县（parentid=市ID）
                List<City> districts = allCities.stream()
                        .filter(district -> district.getLeveltype() == 3 && district.getParentid().equals(city.getId()))
                        .collect(Collectors.toList());
                
                List<CityTreeDTO> districtNodes = new ArrayList<>();
                for (City district : districts) {
                    CityTreeDTO districtNode = new CityTreeDTO();
                    districtNode.setId(district.getId());
                    districtNode.setName(district.getName());
                    districtNodes.add(districtNode);
                }
                
                cityNode.setChildren(districtNodes);
                cityNodes.add(cityNode);
            }
            
            provinceNode.setChildren(cityNodes);
            result.add(provinceNode);
        }
        
        return result;
    }
} 