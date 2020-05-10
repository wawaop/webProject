package com.guet.graduation.cfq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.guet.graduation.cfq")
@MapperScan("com.guet.graduation.cfq.dao")
public class GraduationApplication {
	public static void main(String args[]) {
		SpringApplication.run(GraduationApplication.class, args);
	}
}
