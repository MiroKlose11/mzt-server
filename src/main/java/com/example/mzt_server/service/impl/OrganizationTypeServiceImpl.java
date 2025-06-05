package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.OrganizationTypeDTO;
import com.example.mzt_server.entity.OrganizationType;
import com.example.mzt_server.mapper.OrganizationTypeMapper;
import com.example.mzt_server.service.OrganizationTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 机构类型服务实现类
 */
@Service
public class OrganizationTypeServiceImpl extends ServiceImpl<OrganizationTypeMapper, OrganizationType> implements OrganizationTypeService {
    @Override
    public List<OrganizationTypeDTO> listAll() {
        return list().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public OrganizationTypeDTO getById(Integer id) {
        OrganizationType type = super.getById(id);
        return type == null ? null : toDTO(type);
    }

    @Override
    public boolean saveOrganizationType(OrganizationTypeDTO dto) {
        // 检查code是否已存在
        if (isCodeExists(dto.getCode(), null)) {
            return false;
        }
        
        OrganizationType type = new OrganizationType();
        BeanUtils.copyProperties(dto, type);
        return save(type);
    }

    @Override
    public boolean updateOrganizationType(OrganizationTypeDTO dto) {
        // 检查code是否已存在（排除自身）
        if (isCodeExists(dto.getCode(), dto.getId())) {
            return false;
        }
        
        OrganizationType type = super.getById(dto.getId());
        if (type == null) {
            return false;
        }
        
        BeanUtils.copyProperties(dto, type);
        return updateById(type);
    }

    @Override
    public boolean removeOrganizationType(Integer id) {
        return removeById(id);
    }
    
    @Override
    public String getTypeNameById(Integer typeId) {
        if (typeId == null) {
            return null;
        }
        
        OrganizationType type = super.getById(typeId);
        return type == null ? null : type.getName();
    }
    
    /**
     * 检查code是否已存在
     * @param code 类型唯一标识
     * @param excludeId 排除的ID（更新时使用）
     * @return 是否存在
     */
    private boolean isCodeExists(String code, Integer excludeId) {
        LambdaQueryWrapper<OrganizationType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrganizationType::getCode, code);
        
        if (excludeId != null) {
            queryWrapper.ne(OrganizationType::getId, excludeId);
        }
        
        return count(queryWrapper) > 0;
    }
    
    /**
     * 将实体转换为DTO
     * @param type 机构类型实体
     * @return 机构类型DTO
     */
    private OrganizationTypeDTO toDTO(OrganizationType type) {
        OrganizationTypeDTO dto = new OrganizationTypeDTO();
        BeanUtils.copyProperties(type, dto);
        return dto;
    }
} 