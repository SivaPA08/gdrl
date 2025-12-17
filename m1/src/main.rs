use actix_web::{App, HttpResponse, HttpServer, Responder, web};

async fn health() -> impl Responder {
    HttpResponse::Ok().body("OK")
}

async fn hello(path: web::Path<String>) -> impl Responder {
    format!("Hello {}", path)
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    HttpServer::new(|| {
        App::new()
            .route("/health", web::get().to(health))
            .route("/hello/{name}", web::get().to(hello))
    })
    .bind(("127.0.0.1", 5000))?
    .run()
    .await
}
