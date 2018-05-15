package com.ppx.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("boot/rest")
public class RestTemplateController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@RequestMapping("/testRest")
	public void testRest() {
		
		//restTemplate.post
		
	}

}
