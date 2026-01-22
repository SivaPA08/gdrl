package com.siva.main.gw;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.siva.main.db.UrlRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class RouteRegistry {

	private static final Logger log = LoggerFactory.getLogger(RouteRegistry.class);

	private final UrlRepo repo;
	private final List<RouteDef> routes = new CopyOnWriteArrayList<>();

	public RouteRegistry(UrlRepo repo) {
		this.repo = repo;
		// initial load
		refresh().subscribe(routeList -> {
			routes.clear();
			routes.addAll(routeList);
			log.info("Initial routes loaded: {}", routeList.size());
			printRoutes();
		});
		// periodic refresh
		Flux.interval(Duration.ofSeconds(10))
				.flatMap(t -> refresh())
				.subscribe(routeList -> {
					routes.clear();
					routes.addAll(routeList);
					log.info("Periodic refresh: {} routes loaded", routeList.size());
				});
	}

	public Mono<List<RouteDef>> refresh() {
		return repo.findAll()
				.doOnNext(entity -> log.debug("Loaded route: {} -> {}",
						entity.getUrlPattern(), entity.getTargetUrl()))
				.map(RouteDef::new)
				.collectList()
				.doOnNext(list -> log.info("Total routes loaded from DB: {}", list.size()));
	}

	public List<RouteDef> getRoutes() {
		return routes;
	}

	public Mono<RouteDef> findMatch(String path, String method) {
		log.debug("Finding match for path: {}, method: {}", path, method);
		for (RouteDef r : routes) {
			if (r.matches(path, method)) {
				log.debug("Found match: {} -> {}", r.getPattern(), r.getTargetBase());
				return Mono.just(r);
			}
		}
		log.debug("No route found for path: {}, method: {}", path, method);
		return Mono.empty();
	}

	public void printRoutes() {
		log.info("=== Currently Loaded Routes ===");
		for (RouteDef route : routes) {
			log.info("Pattern: {}, Method: {}, Target: {}, Auth: {}",
					route.getPattern(),
					route.getEntity().getHttpMethod(),
					route.getTargetBase(),
					route.isAuthRequired());
		}
		log.info("=== Total: {} routes ===", routes.size());
	}
}
