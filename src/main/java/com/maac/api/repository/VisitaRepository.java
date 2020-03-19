package com.maac.api.repository;

import com.maac.api.domain.Visita;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Visita entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VisitaRepository extends MongoRepository<Visita, String> {
}
