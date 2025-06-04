package com.example.mzt_server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mzt_server.dto.OrganizationDTO;
import com.example.mzt_server.entity.Organization;

import java.util.List;

/**
 * 机构服务接口
 */
public interface OrganizationService extends IService<Organization> {
    List<OrganizationDTO> listAll();
    OrganizationDTO getById(Integer id);
    boolean saveOrganization(OrganizationDTO dto);
    boolean updateOrganization(OrganizationDTO dto);
    boolean removeOrganization(Integer id);
} 