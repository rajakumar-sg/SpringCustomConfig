package com.example.springcustomconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.EnableConfigServer;

import java.io.File;

@SpringBootApplication
@EnableConfigServer
@EnableConfigurationProperties(VaultProxyEnvProperties.class)
public class SpringCustomConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCustomConfigApplication.class, args);
    }
}
