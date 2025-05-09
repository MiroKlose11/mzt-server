package com.example.cms_mzt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 美职通CMS后台管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.example.cms_mzt.mapper")
public class CmsMztApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsMztApplication.class, args);
	}

}
