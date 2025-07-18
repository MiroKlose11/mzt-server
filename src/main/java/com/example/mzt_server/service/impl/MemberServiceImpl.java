package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.MemberDTO;
import com.example.mzt_server.entity.Member;
import com.example.mzt_server.entity.Organization;
import com.example.mzt_server.entity.Role;
import com.example.mzt_server.entity.Title;
import com.example.mzt_server.mapper.MemberMapper;
import com.example.mzt_server.mapper.MemberRoleMapper;
import com.example.mzt_server.mapper.MemberTitleMapper;
import com.example.mzt_server.mapper.OrganizationMapper;
import com.example.mzt_server.mapper.RoleMapper;
import com.example.mzt_server.mapper.TitleMapper;
import com.example.mzt_server.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 成员服务实现类
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    
    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private TitleMapper titleMapper;
    
    @Autowired
    private MemberRoleMapper memberRoleMapper;
    
    @Autowired
    private MemberTitleMapper memberTitleMapper;
    
    @Autowired
    private OrganizationMapper organizationMapper;
    
    @Override
    public IPage<MemberDTO> pageMember(long current, long size, String name, Integer gender, 
                                    Integer cityId, Integer roleId, Integer status, Integer weight, Integer isElite, Integer userId) {
        Page<Member> page = new Page<>(current, size);
        IPage<Member> memberPage = baseMapper.selectMemberPage(page, name, gender, cityId, roleId, status, weight, isElite, userId);
        
        // 转换为DTO
        IPage<MemberDTO> dtoPage = memberPage.convert(member -> {
            MemberDTO dto = new MemberDTO();
            BeanUtils.copyProperties(member, dto);
            
            // 组装 organizationName
            String orgName = null;
            if (member.getOrganizationId() != null) {
                Organization org = organizationMapper.selectById(member.getOrganizationId());
                if (org != null) {
                    orgName = org.getName();
                }
            }
            if (orgName == null || orgName.isEmpty()) {
                orgName = member.getOrganization();
            }
            dto.setOrganizationName(orgName);
            
            // 获取角色列表
            List<Role> roles = roleMapper.selectRolesByMemberId(member.getId());
            dto.setRoles(roles);
            dto.setRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));
            
            // 获取头衔列表
            List<Title> titles = titleMapper.selectTitlesByMemberId(member.getId());
            dto.setTitles(titles);
            dto.setTitleIds(titles.stream().map(Title::getId).collect(Collectors.toList()));
            
            return dto;
        });
        
        return dtoPage;
    }
    
    @Override
    public MemberDTO getMemberById(Integer id) {
        Member member = getById(id);
        if (member == null) {
            return null;
        }
        
        MemberDTO dto = new MemberDTO();
        BeanUtils.copyProperties(member, dto);
        
        // 组装 organizationName
        String orgName = null;
        if (member.getOrganizationId() != null) {
            Organization org = organizationMapper.selectById(member.getOrganizationId());
            if (org != null) {
                orgName = org.getName();
            }
        }
        if (orgName == null || orgName.isEmpty()) {
            orgName = member.getOrganization();
        }
        dto.setOrganizationName(orgName);
        
        // 获取角色列表
        List<Role> roles = roleMapper.selectRolesByMemberId(id);
        dto.setRoles(roles);
        dto.setRoleIds(roles.stream().map(Role::getId).collect(Collectors.toList()));
        
        // 获取头衔列表
        List<Title> titles = titleMapper.selectTitlesByMemberId(id);
        dto.setTitles(titles);
        dto.setTitleIds(titles.stream().map(Title::getId).collect(Collectors.toList()));
        
        return dto;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveMember(MemberDTO memberDTO) {
        // 保存成员基本信息
        Member member = new Member();
        BeanUtils.copyProperties(memberDTO, member);
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());
        if (member.getStatus() == null) {
            member.setStatus(1); // 默认启用
        }
        
        boolean saved = save(member);
        if (!saved) {
            return false;
        }
        
        // 保存成员角色关联
        if (!CollectionUtils.isEmpty(memberDTO.getRoleIds())) {
            memberRoleMapper.batchInsert(member.getId(), memberDTO.getRoleIds());
        }
        
        // 保存成员头衔关联
        if (!CollectionUtils.isEmpty(memberDTO.getTitleIds())) {
            memberTitleMapper.batchInsert(member.getId(), memberDTO.getTitleIds());
        }
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMember(MemberDTO memberDTO) {
        // 更新成员基本信息
        Member member = getById(memberDTO.getId());
        if (member == null) {
            return false;
        }
        
        BeanUtils.copyProperties(memberDTO, member);
        member.setUpdatedAt(LocalDateTime.now());
        
        boolean updated = updateById(member);
        if (!updated) {
            return false;
        }
        
        // 更新成员角色关联
        memberRoleMapper.deleteByMemberId(member.getId());
        if (!CollectionUtils.isEmpty(memberDTO.getRoleIds())) {
            memberRoleMapper.batchInsert(member.getId(), memberDTO.getRoleIds());
        }
        
        // 更新成员头衔关联
        memberTitleMapper.deleteByMemberId(member.getId());
        if (!CollectionUtils.isEmpty(memberDTO.getTitleIds())) {
            memberTitleMapper.batchInsert(member.getId(), memberDTO.getTitleIds());
        }
        
        return true;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeMember(Integer id) {
        // 删除成员角色关联
        memberRoleMapper.deleteByMemberId(id);
        
        // 删除成员头衔关联
        memberTitleMapper.deleteByMemberId(id);
        
        // 删除成员
        return removeById(id);
    }
} 