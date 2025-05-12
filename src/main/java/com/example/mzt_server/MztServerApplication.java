package com.example.mzt_server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 美职通CMS后台管理系统启动类
 */
@SpringBootApplication
@MapperScan("com.example.mzt_server.mapper")
public class MztServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MztServerApplication.class, args);
	}

} 