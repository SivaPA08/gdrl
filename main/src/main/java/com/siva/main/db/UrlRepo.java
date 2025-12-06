package com.siva.main.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepo extends JpaRepository<UrlEntity, Integer> {
	List<UrlEntity> findByName(String microserviceId);
}
