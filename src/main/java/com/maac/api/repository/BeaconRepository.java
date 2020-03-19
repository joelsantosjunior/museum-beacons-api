package com.maac.api.repository;

import com.maac.api.domain.Beacon;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Beacon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BeaconRepository extends MongoRepository<Beacon, String> {
}
