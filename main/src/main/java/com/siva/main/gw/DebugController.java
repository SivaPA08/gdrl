package com.siva.main.gw;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class DebugController {

	private final RouteRegistry routeRegistry;

	public DebugController(RouteRegistry routeRegistry) {
		this.routeRegistry = routeRegistry;
	}

	@GetMapping("/debug/routes")
	public Mono<List<String>> getRoutes() {
		return Mono.just(
				routeRegistry.getRoutes().stream()
						.map(r -> String.format("%s %s -> %s (auth: %s)",
								r.getEntity().getHttpMethod(),
								r.getPattern(),
								r.getTargetBase(),
								r.isAuthRequired()))
						.collect(Collectors.toList()));
	}

	@GetMapping("/debug/health")
	public Mono<String> health() {
		return Mono.just("Gateway is running. Routes count: " + routeRegistry.getRoutes().size());
	}
}
