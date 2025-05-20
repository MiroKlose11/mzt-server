package com.example.mzt_server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mzt_server.entity.Channel;
import com.example.mzt_server.mapper.ChannelMapper;
import com.example.mzt_server.service.IChannelService;
import org.springframework.stereotype.Service;

/**
 * 栏目表Service实现类
 * 实现栏目（频道）的基础增删改查服务
 */
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel> implements IChannelService {
} 