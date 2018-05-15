package com.ppx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ppx.mapper")
public class SpringBootRunner {

	  public static void main(String[] args) {
		
		  SpringApplication.run(SpringBootRunner.class, args);
	}
}
