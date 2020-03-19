package com.maac.api.web.rest;

import com.maac.api.domain.Visitante;
import com.maac.api.service.VisitanteService;
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
 * REST controller for managing {@link com.maac.api.domain.Visitante}.
 */
@RestController
@RequestMapping("/api")
public class VisitanteResource {

    private final Logger log = LoggerFactory.getLogger(VisitanteResource.class);

    private static final String ENTITY_NAME = "visitante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisitanteService visitanteService;

    public VisitanteResource(VisitanteService visitanteService) {
        this.visitanteService = visitanteService;
    }

    /**
     * {@code POST  /visitantes} : Create a new visitante.
     *
     * @param visitante the visitante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new visitante, or with status {@code 400 (Bad Request)} if the visitante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/visitantes")
    public ResponseEntity<Visitante> createVisitante(@Valid @RequestBody Visitante visitante) throws URISyntaxException {
        log.debug("REST request to save Visitante : {}", visitante);
        if (visitante.getId() != null) {
            throw new BadRequestAlertException("A new visitante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Visitante result = visitanteService.save(visitante);
        return ResponseEntity.created(new URI("/api/visitantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /visitantes} : Updates an existing visitante.
     *
     * @param visitante the visitante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated visitante,
     * or with status {@code 400 (Bad Request)} if the visitante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the visitante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/visitantes")
    public ResponseEntity<Visitante> updateVisitante(@Valid @RequestBody Visitante visitante) throws URISyntaxException {
        log.debug("REST request to update Visitante : {}", visitante);
        if (visitante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Visitante result = visitanteService.save(visitante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, visitante.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /visitantes} : get all the visitantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of visitantes in body.
     */
    @GetMapping("/visitantes")
    public ResponseEntity<List<Visitante>> getAllVisitantes(Pageable pageable) {
        log.debug("REST request to get a page of Visitantes");
        Page<Visitante> page = visitanteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /visitantes/:id} : get the "id" visitante.
     *
     * @param id the id of the visitante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the visitante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/visitantes/{id}")
    public ResponseEntity<Visitante> getVisitante(@PathVariable String id) {
        log.debug("REST request to get Visitante : {}", id);
        Optional<Visitante> visitante = visitanteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(visitante);
    }

    /**
     * {@code DELETE  /visitantes/:id} : delete the "id" visitante.
     *
     * @param id the id of the visitante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/visitantes/{id}")
    public ResponseEntity<Void> deleteVisitante(@PathVariable String id) {
        log.debug("REST request to delete Visitante : {}", id);
        visitanteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
