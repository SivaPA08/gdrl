package com.siva.main.jwt;

public class JwtService {
	private final String secret = "top-secret";
	private final long ttlSeconds = 3600; // 1 hr

	public String loginJwt(String userId, String role) throws Exception {
		return JwtUtil.createJwt(userId, role, secret, ttlSeconds);
	}

	public String validateToken(String token) throws Exception {
		return JwtUtil.verifyJwt(token, secret);
	}

}
