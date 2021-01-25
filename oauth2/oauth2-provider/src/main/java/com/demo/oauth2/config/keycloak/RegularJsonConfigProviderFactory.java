package com.demo.oauth2.config.keycloak;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import org.jboss.logging.Logger;
import org.keycloak.Config.ConfigProvider;
import org.keycloak.services.ServicesLogger;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;

public class RegularJsonConfigProviderFactory extends JsonConfigProviderFactory {
	private static final Logger LOG = Logger.getLogger(JsonConfigProviderFactory.class);
	
	@Autowired
	static KeycloakServerProperties keycloakServerProperties;

	@Override
	public Optional<ConfigProvider> create() {
		JsonNode node = null;
		try {
			URL configUrl = Thread.currentThread().getContextClassLoader()
					.getResource(keycloakServerProperties.getInitFile());
			if (configUrl != null) {
				File f = new File(configUrl.getFile());
				if (f.isFile()) {
					ServicesLogger.LOGGER.loadingFrom(f.getAbsolutePath());
					node = JsonSerialization.mapper.readTree(f);
				}
			}

			if (node == null) {
				configUrl = Thread.currentThread().getContextClassLoader()
						.getResource(keycloakServerProperties.getInitFile());
				if (configUrl != null) {
					ServicesLogger.LOGGER.loadingFrom(configUrl);
					node = JsonSerialization.mapper.readTree(configUrl);
				}
			}
		} catch (IOException e) {
			LOG.warn("Failed to load JSON config", e);
		}

		return createJsonProvider(node);
	}
}
