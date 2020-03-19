package com.maac.api.service;

import com.maac.api.domain.Beacon;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Beacon}.
 */
public interface BeaconService {

    /**
     * Save a beacon.
     *
     * @param beacon the entity to save.
     * @return the persisted entity.
     */
    Beacon save(Beacon beacon);

    /**
     * Get all the beacons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Beacon> findAll(Pageable pageable);

    /**
     * Get the "id" beacon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Beacon> findOne(String id);

    /**
     * Delete the "id" beacon.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
