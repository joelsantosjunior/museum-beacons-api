package com.maac.api.web.rest;

import com.maac.api.domain.Visita;
import com.maac.api.service.VisitaService;
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
 * REST controller for managing {@link com.maac.api.domain.Visita}.
 */
@RestController
@RequestMapping("/api")
public class VisitaResource {

    private final Logger log = LoggerFactory.getLogger(VisitaResource.class);

    private static final String ENTITY_NAME = "visita";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisitaService visitaService;

    public VisitaResource(VisitaService visitaService) {
        this.visitaService = visitaService;
    }

    /**
     * {@code POST  /visitas} : Create a new visita.
     *
     * @param visita the visita to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visita, or with status {@code 400 (Bad Request)} if the visita has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visitas")
    public ResponseEntity<Visita> createVisita(@Valid @RequestBody Visita visita) throws URISyntaxException {
        log.debug("REST request to save Visita : {}", visita);
        if (visita.getId() != null) {
            throw new BadRequestAlertException("A new visita cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Visita result = visitaService.save(visita);
        return ResponseEntity.created(new URI("/api/visitas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visitas} : Updates an existing visita.
     *
     * @param visita the visita to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visita,
     * or with status {@code 400 (Bad Request)} if the visita is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visita couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visitas")
    public ResponseEntity<Visita> updateVisita(@Valid @RequestBody Visita visita) throws URISyntaxException {
        log.debug("REST request to update Visita : {}", visita);
        if (visita.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Visita result = visitaService.save(visita);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visita.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /visitas} : get all the visitas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visitas in body.
     */
    @GetMapping("/visitas")
    public ResponseEntity<List<Visita>> getAllVisitas(Pageable pageable) {
        log.debug("REST request to get a page of Visitas");
        Page<Visita> page = visitaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visitas/:id} : get the "id" visita.
     *
     * @param id the id of the visita to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visita, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visitas/{id}")
    public ResponseEntity<Visita> getVisita(@PathVariable String id) {
        log.debug("REST request to get Visita : {}", id);
        Optional<Visita> visita = visitaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visita);
    }

    /**
     * {@code DELETE  /visitas/:id} : delete the "id" visita.
     *
     * @param id the id of the visita to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visitas/{id}")
    public ResponseEntity<Void> deleteVisita(@PathVariable String id) {
        log.debug("REST request to delete Visita : {}", id);
        visitaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
