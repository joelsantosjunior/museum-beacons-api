package com.maac.api.repository;

import com.maac.api.domain.Visitante;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Visitante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitanteRepository extends MongoRepository<Visitante, String> {
}
