package com.maac.api.service.impl;

import com.maac.api.service.BeaconService;
import com.maac.api.domain.Beacon;
import com.maac.api.repository.BeaconRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Beacon}.
 */
@Service
public class BeaconServiceImpl implements BeaconService {

    private final Logger log = LoggerFactory.getLogger(BeaconServiceImpl.class);

    private final BeaconRepository beaconRepository;

    public BeaconServiceImpl(BeaconRepository beaconRepository) {
        this.beaconRepository = beaconRepository;
    }

    /**
     * Save a beacon.
     *
     * @param beacon the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Beacon save(Beacon beacon) {
        log.debug("Request to save Beacon : {}", beacon);
        return beaconRepository.save(beacon);
    }

    /**
     * Get all the beacons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Beacon> findAll(Pageable pageable) {
        log.debug("Request to get all Beacons");
        return beaconRepository.findAll(pageable);
    }

    /**
     * Get one beacon by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Beacon> findOne(String id) {
        log.debug("Request to get Beacon : {}", id);
        return beaconRepository.findById(id);
    }

    /**
     * Delete the beacon by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Beacon : {}", id);
        beaconRepository.deleteById(id);
    }
}
