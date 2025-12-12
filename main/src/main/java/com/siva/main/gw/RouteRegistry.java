package com.siva.main.gw;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import com.siva.main.db.UrlRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RouteRegistry {

	private final UrlRepo repo;
	private final List<RouteDef> routes = new CopyOnWriteArrayList<>();

	public RouteRegistry(UrlRepo repo) {
		this.repo = repo;
		// inital load
		refresh().subscribe(routeList -> {
			routes.clear();
			routes.addAll(routeList);
		});
		// preiodic refresh
		Flux.interval(Duration.ofSeconds(10))
				.flatMap(t -> refresh())
				.subscribe(routeList -> {
					routes.clear();
					routes.addAll(routeList);
				});
	}

	public Mono<List<RouteDef>> refresh() {
		return repo.findAll().map(RouteDef::new).collectList();
	}

	public List<RouteDef> getRoutes() {
		return routes;
	}

	public Mono<RouteDef> findMatch(String path, String method) {
		for (RouteDef r : routes) {
			if (r.matches(path, method)) {
				return Mono.just(r);
			}
		}
		return Mono.empty();
	}

}
