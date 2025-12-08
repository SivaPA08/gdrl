package com.siva.main.db;

import jakarta.persistence.Entity;

@Entity
// @Table(name = "url")
public class UrlEntity {

	private String microserviceId;
	// important cols
	private String urlPattern;
	private String httpMethod;
	private String targetUrl; // address of the url
	private boolean authRequired;
	private String role;
	// imp cols ends
	private String microserviceUrl;

	private String name;

	public String getMicroserviceId() {
		return microserviceId;
	}

	public String getMicroserviceUrl() {
		return microserviceUrl;
	}

	public String getName() {
		return name;
	}

	public void setMicroserviceId(String microserviceId) {
		this.microserviceId = microserviceId;
	}

	public void setMicroserviceUrl(String microserviceUrl) {
		this.microserviceUrl = microserviceUrl;
	}

	public void setName(String name) {
		this.name = name;
	}
}
