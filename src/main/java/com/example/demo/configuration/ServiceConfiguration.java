package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
    basePackages = {"com.example.demo.repository"},
    enableDefaultTransactions = false
)
public class ServiceConfiguration {

}
