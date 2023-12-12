package com.crosskey.mortgage.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crosskey.mortgage.core.service.CoreCalculatorService;

@Configuration
public class CoreServiceConfig {

  @Bean
  public CoreCalculatorService coreCalculatorService() {
    return new CoreCalculatorService();
  }
}
