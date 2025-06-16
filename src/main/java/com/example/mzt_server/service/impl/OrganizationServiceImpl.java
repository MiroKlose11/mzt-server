package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.OrganizationDTO;
import com.example.mzt_server.entity.Organization;
import com.example.mzt_server.mapper.OrganizationMapper;
import com.example.mzt_server.service.CityService;
import com.example.mzt_server.service.OrganizationService;
import com.example.mzt_server.service.OrganizationTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机构服务实现类
 */
@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {
    @Autowired
    private CityService cityService;
    
    @Autowired
    private OrganizationTypeService organizationTypeService;
    
    @Override
    public List<OrganizationDTO> listAll() {
        return list(new LambdaQueryWrapper<>()).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public OrganizationDTO getById(Integer id) {
        return getDTOById(id);
    }

    public OrganizationDTO getDTOById(Integer id) {
        Organization org = super.getById(id);
        return org == null ? null : toDTO(org);
    }

    @Override
    public boolean saveOrganization(OrganizationDTO dto) {
        Organization org = new Organization();
        BeanUtils.copyProperties(dto, org);
        org.setCreatedAt(LocalDateTime.now());
        org.setUpdatedAt(LocalDateTime.now());
        if (org.getStatus() == null) org.setStatus(1);
        return save(org);
    }

    @Override
    public boolean updateOrganization(OrganizationDTO dto) {
        Organization org = super.getById(dto.getId());
        if (org == null) return false;
        BeanUtils.copyProperties(dto, org);
        org.setUpdatedAt(LocalDateTime.now());
        return updateById(org);
    }

    @Override
    public boolean removeOrganization(Integer id) {
        return removeById(id);
    }

    private OrganizationDTO toDTO(Organization org) {
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(org, dto);
        
        // 设置城市名称
        if (org.getCityId() != null) {
            dto.setCityName(cityService.getCityNameWithSuffix(org.getCityId()));
        }
        
        // 设置机构类型名称
        if (org.getTypeId() != null) {
            dto.setTypeName(organizationTypeService.getTypeNameById(org.getTypeId()));
        }
        
        return dto;
    }
} 