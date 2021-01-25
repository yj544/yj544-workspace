package com.demo.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().disable()
			.csrf().disable()
			.authorizeRequests(
				auth -> auth.antMatchers(HttpMethod.GET, "/resource/**").hasAuthority("SCOPE_user")
							.antMatchers(HttpMethod.POST, "/resource").hasAuthority("SCOPE_admin")
							.anyRequest().authenticated())
			.oauth2ResourceServer(oauth2 -> oauth2.jwt());
	}
}
