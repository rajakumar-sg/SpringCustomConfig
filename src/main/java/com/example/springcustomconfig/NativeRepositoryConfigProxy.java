package com.example.springcustomconfig;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.cloud.config.server.environment.NativeEnvironmentProperties;
import org.springframework.cloud.config.server.environment.NativeEnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
class NativeRepositoryConfigProxy {

    @Bean
    public EnvironmentRepository vaultProxyEnvironmentRepository(
            VaultProxyEnvProperties vaultProxyEnvProperties,
            ConfigurableEnvironment configurableEnvironment
    ) {
        NativeEnvironmentProperties nativeEnvironmentProperties = new NativeEnvironmentProperties();
        nativeEnvironmentProperties.setSearchLocations(vaultProxyEnvProperties.getSearchLocations());
        return new NativeEnvironmentRepository(configurableEnvironment, nativeEnvironmentProperties, ObservationRegistry.NOOP) {
            @Override
            public Environment findOne(String config, String profile, String label, boolean includeOrigin) {
                System.out.println("Finding config:" + config + ", profile: " + profile + ", label: " + label + ", includeOrigin: " + includeOrigin);


                Environment environment = super.findOne(config, profile, label, includeOrigin);

                Environment proxyEnvironment = new Environment(environment.getName(), environment.getProfiles(), environment.getLabel(), environment.getVersion(), environment.getState());
                environment.getPropertySources().forEach(propertySource -> {
                    Map<Object, Object> source = (Map<Object, Object>)propertySource.getSource();
                    Map<Object, Object> proxy = new HashMap<>();
                    proxy.putAll(source);
                    proxy.put("app.name", "PROXIED-APP-NAME");
                    proxy.put("app.new.prop", "PROXIED-NEW-PROP");
                    PropertySource proxySource = new PropertySource(propertySource.getName(), proxy, propertySource.getOriginalPropertySource());
                    proxyEnvironment.add(proxySource);
                });
                return proxyEnvironment;
            }
        };
    }
}