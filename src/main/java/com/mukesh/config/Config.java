package com.mukesh.config;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.mukesh.controller.CounterOperatorController;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.Operator;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.config.runtime.DefaultConfigurationService;

@Configuration
public class Config {

  @Bean
  public KubernetesClient kubernetesClient() {
    return new DefaultKubernetesClient();
  }

  @Bean
  public CounterOperatorController customServiceController(KubernetesClient client) {
    return new CounterOperatorController(client);
  }

  //  Register all controller beans
  @Bean
  public Operator operator(KubernetesClient client, List<ResourceController> controllers) {
    Operator operator = new Operator(client, DefaultConfigurationService.instance());
    controllers.forEach(operator::register);
    return operator;
  }
  
  @Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
