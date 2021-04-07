package com.mukesh.service;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.ShortNames;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("mukesh.operator")
@Version("v1")
@ShortNames("nginx")
public class Nginx extends CustomResource<ServiceSpec, Void> implements Namespaced {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4955470590585569014L;
	}
