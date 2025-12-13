package com.siva.main.gw;

import org.springframework.http.server.reactive.ServerHttpRequest;

public class PathRewriteUtil {
	private PathRewriteUtil() {
	}

	public static String buildTargetUrl(ServerHttpRequest req, RouteDef route) {
		String base = route.getTargetBase();
		String path = req.getURI().getPath();
		String query = req.getURI().getRawQuery();
		if (base.endsWith("/") && path.startsWith("/")) {
			base = base.substring(0, base.length() - 1);
		}
		return base + path + (query == null ? "" : '?' + query);
	}

}
