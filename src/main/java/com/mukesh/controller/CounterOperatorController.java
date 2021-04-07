package com.mukesh.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mukesh.service.Nginx;

import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePort;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.javaoperatorsdk.operator.api.Context;
import io.javaoperatorsdk.operator.api.Controller;
import io.javaoperatorsdk.operator.api.DeleteControl;
import io.javaoperatorsdk.operator.api.ResourceController;
import io.javaoperatorsdk.operator.api.UpdateControl;

/** A very simple sample controller that creates a service with a label. */
@Controller
public class CounterOperatorController implements ResourceController<Nginx> {

	public static final String KIND = "Nginx";
	private static final Logger log = LoggerFactory.getLogger(CounterOperatorController.class);

	@Autowired
	private final KubernetesClient kubernetesClient;

	@Autowired
	private RestTemplate restTemplate;

	public CounterOperatorController(KubernetesClient kubernetesClient) {
		this.kubernetesClient = kubernetesClient;
	}

	@Override
	public DeleteControl deleteResource(Nginx resource, Context<Nginx> context) {
		log.info("Execution deleteResource for: {}", resource.getMetadata().getName());
		String deccreaseUrl = "http://counterservice/counter/decrease";
		callCounterService(deccreaseUrl);
		return DeleteControl.DEFAULT_DELETE;
	}

	@Override
	public UpdateControl<Nginx> createOrUpdateResource(Nginx resource, Context<Nginx> context) {
		log.info("Execution createOrUpdateResource for: {}", resource.getMetadata().getName());

		String increaseUrl = "http://counterservice/counter/increase";

		ResponseEntity<String> response = callCounterService(increaseUrl);

		ServicePort servicePort = new ServicePort();
		servicePort.setPort(8080);
		ServiceSpec serviceSpec = new ServiceSpec();
		serviceSpec.setPorts(Collections.singletonList(servicePort));

		kubernetesClient.services().inNamespace(resource.getMetadata().getNamespace())
				.createOrReplace(new ServiceBuilder().withNewMetadata().withName(resource.getSpec().getName())
						.addToLabels(response.getBody(), resource.getSpec().getLabel()).endMetadata()
						.withSpec(serviceSpec).build());
		return UpdateControl.updateCustomResource(resource);
	}

	/**
	 * @return
	 */
	private ResponseEntity<String> callCounterService(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<String> entity = new HttpEntity<>(headers);
		log.debug("In CounterOperatorController.callCounterService while calling the Get GeoCode request" + url);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		return response;
	}
}
