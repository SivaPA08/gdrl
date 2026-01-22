package com.siva.main.gw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;

import com.siva.main.db.UrlEntity;

public class RouteDef {

	private static final Logger log = LoggerFactory.getLogger(RouteDef.class);
	private static final PathPatternParser parser = new PathPatternParser();

	private final UrlEntity entity;
	private final String rawPattern;
	private final PathPattern compiledPattern;
	private final String httpMethod;
	private final String targetBase;
	private final boolean authRequired;
	private final String role;

	public RouteDef(UrlEntity e) {
		this.entity = e;
		this.rawPattern = e.getUrlPattern();
		this.compiledPattern = (rawPattern == null ? null : parser.parse(rawPattern));

		this.httpMethod = e.getHttpMethod();
		this.targetBase = e.getTargetUrl();
		this.authRequired = e.isAuthRequired();
		this.role = e.getRole();

		log.debug("Created RouteDef: pattern={}, method={}, target={}",
				rawPattern, httpMethod, targetBase);
	}

	public boolean matches(String incomingPath, String incomingMethod) {
		if (compiledPattern == null) {
			log.debug("Pattern is null for: {}", rawPattern);
			return false;
		}

		boolean pathMatches = compiledPattern.matches(PathContainer.parsePath(incomingPath));

		if (!pathMatches) {
			log.debug("Path doesn't match: {} (pattern: {})", incomingPath, rawPattern);
			return false;
		}

		if (httpMethod == null || httpMethod.isBlank() || httpMethod.equalsIgnoreCase("ANY")) {
			log.debug("Path matches, method check skipped (ANY): {} -> {}", incomingPath, rawPattern);
			return true;
		}

		boolean methodMatches = incomingMethod.equalsIgnoreCase(httpMethod);
		log.debug("Method check: {} (expected: {}, got: {})",
				methodMatches, httpMethod, incomingMethod);

		return methodMatches;
	}

	public UrlEntity getEntity() {
		return entity;
	}

	public String getPattern() {
		return rawPattern;
	}

	public String getTargetBase() {
		return targetBase;
	}

	public boolean isAuthRequired() {
		return authRequired;
	}

	public String getRole() {
		return role;
	}
}
