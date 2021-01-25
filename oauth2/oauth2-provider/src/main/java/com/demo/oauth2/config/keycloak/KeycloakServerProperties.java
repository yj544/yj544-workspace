package com.demo.oauth2.config.keycloak;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "keycloak.server")
@Getter
@Setter
public class KeycloakServerProperties {
	String contextPath = "/auth";
	String realmImportFile = "auth-server.json";
	String initFile = "keycloak-server.json";
	AdminUser adminUser = new AdminUser();
	
	@Getter
	@Setter
	public static class AdminUser {
		String username = "";
		String password = "";
	}
}
