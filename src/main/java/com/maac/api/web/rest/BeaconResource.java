package com.maac.api.web.rest;

import com.maac.api.domain.Beacon;
import com.maac.api.service.BeaconService;
import com.maac.api.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.maac.api.domain.Beacon}.
 */
@RestController
@RequestMapping("/api")
public class BeaconResource {

    private final Logger log = LoggerFactory.getLogger(BeaconResource.class);

    private static final String ENTITY_NAME = "beacon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BeaconService beaconService;

    public BeaconResource(BeaconService beaconService) {
        this.beaconService = beaconService;
    }

    /**
     * {@code POST  /beacons} : Create a new beacon.
     *
     * @param beacon the beacon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new beacon, or with status {@code 400 (Bad Request)} if the beacon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/beacons")
    public ResponseEntity<Beacon> createBeacon(@Valid @RequestBody Beacon beacon) throws URISyntaxException {
        log.debug("REST request to save Beacon : {}", beacon);
        if (beacon.getId() != null) {
            throw new BadRequestAlertException("A new beacon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Beacon result = beaconService.save(beacon);
        return ResponseEntity.created(new URI("/api/beacons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /beacons} : Updates an existing beacon.
     *
     * @param beacon the beacon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated beacon,
     * or with status {@code 400 (Bad Request)} if the beacon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the beacon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/beacons")
    public ResponseEntity<Beacon> updateBeacon(@Valid @RequestBody Beacon beacon) throws URISyntaxException {
        log.debug("REST request to update Beacon : {}", beacon);
        if (beacon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Beacon result = beaconService.save(beacon);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, beacon.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /beacons} : get all the beacons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of beacons in body.
     */
    @GetMapping("/beacons")
    public ResponseEntity<List<Beacon>> getAllBeacons(Pageable pageable) {
        log.debug("REST request to get a page of Beacons");
        Page<Beacon> page = beaconService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /beacons/:id} : get the "id" beacon.
     *
     * @param id the id of the beacon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the beacon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/beacons/{id}")
    public ResponseEntity<Beacon> getBeacon(@PathVariable String id) {
        log.debug("REST request to get Beacon : {}", id);
        Optional<Beacon> beacon = beaconService.findOne(id);
        return ResponseUtil.wrapOrNotFound(beacon);
    }

    /**
     * {@code DELETE  /beacons/:id} : delete the "id" beacon.
     *
     * @param id the id of the beacon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/beacons/{id}")
    public ResponseEntity<Void> deleteBeacon(@PathVariable String id) {
        log.debug("REST request to delete Beacon : {}", id);
        beaconService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
