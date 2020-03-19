package com.maac.api.service.impl;

import com.maac.api.service.VisitanteService;
import com.maac.api.domain.Visitante;
import com.maac.api.repository.VisitanteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Visitante}.
 */
@Service
public class VisitanteServiceImpl implements VisitanteService {

    private final Logger log = LoggerFactory.getLogger(VisitanteServiceImpl.class);

    private final VisitanteRepository visitanteRepository;

    public VisitanteServiceImpl(VisitanteRepository visitanteRepository) {
        this.visitanteRepository = visitanteRepository;
    }

    /**
     * Save a visitante.
     *
     * @param visitante the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Visitante save(Visitante visitante) {
        log.debug("Request to save Visitante : {}", visitante);
        return visitanteRepository.save(visitante);
    }

    /**
     * Get all the visitantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Visitante> findAll(Pageable pageable) {
        log.debug("Request to get all Visitantes");
        return visitanteRepository.findAll(pageable);
    }

    /**
     * Get one visitante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Visitante> findOne(String id) {
        log.debug("Request to get Visitante : {}", id);
        return visitanteRepository.findById(id);
    }

    /**
     * Delete the visitante by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Visitante : {}", id);
        visitanteRepository.deleteById(id);
    }
}
