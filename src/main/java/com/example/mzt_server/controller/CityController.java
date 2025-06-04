package com.example.mzt_server.controller;

import com.example.mzt_server.common.Result;
import com.example.mzt_server.dto.CityDTO;
import com.example.mzt_server.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 城市数据控制器
 */
@Tag(name = "城市数据", description = "城市数据接口")
@RestController
@RequestMapping("/city")
public class CityController {
    
    @Autowired
    private CityService cityService;
    
    @Operation(summary = "获取城市列表", description = "获取所有城市数据，用于省市区级联选择")
    @GetMapping("/list")
    public Result<List<CityDTO>> getList() {
        List<CityDTO> cityDTOList = cityService.getCityDTOList();
        return Result.success(cityDTOList);
    }
} 