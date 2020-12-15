package com.nlmk.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//@EnableConfigurationProperties
@ConfigurationProperties
@PropertySource("classpath:/application.yml")
@Getter
@Setter
public class GlobalConfig {

    private String name;
    private String baseURL;
    private List<String> servers;
    private boolean enabled;
    private List<Hop> hops;

    @Getter
    @Setter
    public static class Hop{
        private String id;
        private String role;
    }

}

