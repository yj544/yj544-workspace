package com.demo.oauth2.main;

import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

	@GetMapping("/")
	public String index() {
		return "index";
	}
		
	@GetMapping("/resources")
	public String getResources(Principal principal) {
		return "resources";
	}
}
