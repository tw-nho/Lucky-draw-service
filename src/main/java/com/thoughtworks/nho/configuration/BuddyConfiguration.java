package com.thoughtworks.nho.configuration;

import com.thoughtworks.nho.domain.Buddy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "trainee")
public class BuddyConfiguration {

    @Getter
    @Setter
    private List<Buddy> buddies;
}
