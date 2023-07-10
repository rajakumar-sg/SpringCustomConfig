package com.example.springcustomconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.server.support.EnvironmentRepositoryProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("spring.cloud.config.server.vault-proxy")
@Component
public class VaultProxyEnvProperties implements EnvironmentRepositoryProperties {
    private int order;
    private String[] searchLocations;

    @Override
    public void setOrder(int order) {
        this.order = order;
    }

    public void setSearchLocations(String[] searchLocations) {
        this.searchLocations = searchLocations;
    }

    public String[] getSearchLocations() {
        return searchLocations;
    }
}
