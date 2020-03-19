package com.maac.api.service.impl;

import com.maac.api.service.VisitaService;
import com.maac.api.domain.Visita;
import com.maac.api.repository.VisitaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Visita}.
 */
@Service
public class VisitaServiceImpl implements VisitaService {

    private final Logger log = LoggerFactory.getLogger(VisitaServiceImpl.class);

    private final VisitaRepository visitaRepository;

    public VisitaServiceImpl(VisitaRepository visitaRepository) {
        this.visitaRepository = visitaRepository;
    }

    /**
     * Save a visita.
     *
     * @param visita the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Visita save(Visita visita) {
        log.debug("Request to save Visita : {}", visita);
        return visitaRepository.save(visita);
    }

    /**
     * Get all the visitas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Visita> findAll(Pageable pageable) {
        log.debug("Request to get all Visitas");
        return visitaRepository.findAll(pageable);
    }

    /**
     * Get one visita by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Visita> findOne(String id) {
        log.debug("Request to get Visita : {}", id);
        return visitaRepository.findById(id);
    }

    /**
     * Delete the visita by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Visita : {}", id);
        visitaRepository.deleteById(id);
    }
}
