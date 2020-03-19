package com.maac.api.service;

import com.maac.api.domain.Visitante;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Visitante}.
 */
public interface VisitanteService {

    /**
     * Save a visitante.
     *
     * @param visitante the entity to save.
     * @return the persisted entity.
     */
    Visitante save(Visitante visitante);

    /**
     * Get all the visitantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Visitante> findAll(Pageable pageable);

    /**
     * Get the "id" visitante.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Visitante> findOne(String id);

    /**
     * Delete the "id" visitante.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
