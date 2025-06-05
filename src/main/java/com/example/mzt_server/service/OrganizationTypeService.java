package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.OrganizationTypeDTO;
import com.example.mzt_server.entity.OrganizationType;

import java.util.List;

/**
 * 机构类型服务接口
 */
public interface OrganizationTypeService extends IService<OrganizationType> {
    /**
     * 获取所有机构类型列表
     * @return 机构类型DTO列表
     */
    List<OrganizationTypeDTO> listAll();
    
    /**
     * 根据ID获取机构类型
     * @param id 机构类型ID
     * @return 机构类型DTO
     */
    OrganizationTypeDTO getById(Integer id);
    
    /**
     * 保存机构类型
     * @param dto 机构类型DTO
     * @return 是否成功
     */
    boolean saveOrganizationType(OrganizationTypeDTO dto);
    
    /**
     * 更新机构类型
     * @param dto 机构类型DTO
     * @return 是否成功
     */
    boolean updateOrganizationType(OrganizationTypeDTO dto);
    
    /**
     * 删除机构类型
     * @param id 机构类型ID
     * @return 是否成功
     */
    boolean removeOrganizationType(Integer id);
    
    /**
     * 根据ID获取机构类型名称
     * @param typeId 机构类型ID
     * @return 机构类型名称
     */
    String getTypeNameById(Integer typeId);
} 