package com.kh.spring.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ApiForwardController {

	
	@GetMapping("hospital")
	public String hospitalPage() {
		return "api/api";
	}
	
	@GetMapping("sms")
	public String smsPage() {
		return "api/simple_message";
	}
	
	@GetMapping("map")
	public String mapPage() {
		return "api/map";
	}
	
	@GetMapping("charger")
	public String chargerPage() {
		return "api/electric";
	}
	
	@GetMapping("shopping")
	public String shoppingPage() {
		return "api/shopping";
	}
}
