package com.ppx.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@PropertySource("classpath:config/system.yml")
@ConfigurationProperties(prefix="app")
public class SystemConfig {

	 @Value("${version}")
	 @Getter
	 @Setter
	 private String version;
	 
	 @Value("${name}")
	 @Getter
	 @Setter
	 private String name;
	 
	 
	 
}
