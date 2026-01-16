# RouteNet 

RouteNet is a lightweight API Gateway that sits in front of backend services and enforces **JWT-based authentication** and **request rate limiting** before forwarding traffic.  
It is designed for **learning, experimentation, and small-scale systems**, not for pretending to replace Kong, NGINX, or Envoy.

If you’re looking for a simple gateway to understand how auth + rate limiting work together, this is it.

---

## Features

- **JWT Authentication**
  - Validates incoming requests using JSON Web Tokens
  - Rejects unauthorized or expired tokens before hitting backend services

- **Rate Limiting**
  - Limits requests per client (token / IP based, depending on configuration)

- **API Gateway Behavior**
  - Acts as a single entry point for downstream services
  - Forwards valid requests to configured backend routes

---


