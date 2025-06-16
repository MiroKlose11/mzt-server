package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.dto.PositionDTO;
import com.example.mzt_server.entity.Position;
import com.example.mzt_server.mapper.PositionMapper;
import com.example.mzt_server.service.PositionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 岗位服务实现类
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
    @Override
    public List<PositionDTO> listAll() {
        // 按排序值升序排列
        LambdaQueryWrapper<Position> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Position::getSort);
        return list(queryWrapper).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public PositionDTO getById(Integer id) {
        Position position = super.getById(id);
        return position == null ? null : toDTO(position);
    }

    @Override
    public boolean savePosition(PositionDTO dto) {
        Position position = new Position();
        BeanUtils.copyProperties(dto, position);
        position.setCreatedAt(LocalDateTime.now());
        position.setUpdatedAt(LocalDateTime.now());
        if (position.getSort() == null) position.setSort(0);
        if (position.getStatus() == null) position.setStatus(true);
        return save(position);
    }

    @Override
    public boolean updatePosition(PositionDTO dto) {
        Position position = super.getById(dto.getId());
        if (position == null) return false;
        BeanUtils.copyProperties(dto, position);
        position.setUpdatedAt(LocalDateTime.now());
        return updateById(position);
    }

    @Override
    public boolean removePosition(Integer id) {
        return removeById(id);
    }

    private PositionDTO toDTO(Position position) {
        PositionDTO dto = new PositionDTO();
        BeanUtils.copyProperties(position, dto);
        return dto;
    }
} 