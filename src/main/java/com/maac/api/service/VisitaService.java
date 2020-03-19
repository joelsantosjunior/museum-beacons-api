package com.maac.api.service;

import com.maac.api.domain.Visita;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Visita}.
 */
public interface VisitaService {

    /**
     * Save a visita.
     *
     * @param visita the entity to save.
     * @return the persisted entity.
     */
    Visita save(Visita visita);

    /**
     * Get all the visitas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Visita> findAll(Pageable pageable);

    /**
     * Get the "id" visita.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Visita> findOne(String id);

    /**
     * Delete the "id" visita.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
