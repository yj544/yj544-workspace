package com.demo.oauth2.config.keycloak;

import javax.naming.CompositeName;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingException;
import javax.naming.spi.NamingManager;
import javax.servlet.ServletRegistration;
import javax.sql.DataSource;

import org.jboss.resteasy.plugins.server.servlet.HttpServlet30Dispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyContextParameters;
import org.keycloak.platform.PlatformProvider;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedKeycloakConfig {
	@Bean
	ServletRegistrationBean<HttpServlet30Dispatcher> keycloakJaxRsApplication(KeycloakServerProperties properties, DataSource datasource) throws NamingException {
		mockJndiEnvironment(datasource);
		EmbeddedKeycloakApplication.keycloakServerProperties = properties;

		ServletRegistrationBean<HttpServlet30Dispatcher> servlet = new ServletRegistrationBean<>(
				new HttpServlet30Dispatcher());
		servlet.addInitParameter("javax.ws.rs.Application", EmbeddedKeycloakApplication.class.getName());
		servlet.addInitParameter(ResteasyContextParameters.RESTEASY_SERVLET_MAPPING_PREFIX,
				properties.getContextPath());
		servlet.addInitParameter(ResteasyContextParameters.RESTEASY_USE_CONTAINER_FORM_PARAMS, "true");
		servlet.addUrlMappings(properties.getContextPath() + "/*");
		servlet.setLoadOnStartup(1);
		servlet.setAsyncSupported(true);
		return servlet;
	}
	
	@Bean
	FilterRegistrationBean<EmbeddedKeycloakRequestFilter> keycloakSessionManagement(KeycloakServerProperties properties) {
		FilterRegistrationBean filter = new FilterRegistrationBean<>();
		filter.setName("Keycloak Session Management");
		filter.setFilter(new EmbeddedKeycloakRequestFilter());
		filter.addUrlPatterns(properties.getContextPath() + "/*");
		return filter;
	}
	
	private void mockJndiEnvironment(DataSource datasource) throws NamingException {
		NamingManager.setInitialContextFactoryBuilder((env) -> (environment) -> new InitialContext() {
			@Override
			public Object lookup(Name name) {
				return lookup(name.toString());
			}

			@Override
			public Object lookup(String name) {

				if ("spring/datasource".equals(name)) {
					return datasource;
				}

				return null;
			}

			@Override
			public NameParser getNameParser(String name) {
				return CompositeName::new;
			}

			@Override
			public void close() {
				// NOOP
			}
		});
	}
	
}
