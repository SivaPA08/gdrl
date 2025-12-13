//The real problem path rewrite solves
//
// Clients talk to the gateway,not to services.
//
// Example client request:
//
// GET/api/auth/login
//
// Your auth service does NOT have:
//
// /api/auth/login
//
// It has:
//
// /login
//
// So someone must:
//
// Remove/api/auth
//
// Forward only/login
//
// That“someone”is path rewrite.//
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
