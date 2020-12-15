package com.nlmk.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "variables")
//@EnableConfigurationProperties(value = DemoComponent.class)
@PropertySource("classpath:/application.yml")
public class DemoComponent {

    private List<Hop> hops = new ArrayList<>();

    public List<Hop> getHops() {
        return hops;
    }

    public void setHops(List<Hop> hops) {
        this.hops = hops;
    }

    @Override
    public String toString() {
        return "DemoComponent{" +
                "hops=" + hops +
                '}';
    }
}