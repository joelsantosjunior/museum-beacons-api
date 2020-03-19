package com.maac.api.web.rest;

import com.maac.api.MaacBeaconApiApp;
import com.maac.api.domain.Visitante;
import com.maac.api.repository.VisitanteRepository;
import com.maac.api.service.VisitanteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.maac.api.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VisitanteResource} REST controller.
 */
@SpringBootTest(classes = MaacBeaconApiApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class VisitanteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_ID_CELULAR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VisitanteRepository visitanteRepository;

    @Autowired
    private VisitanteService visitanteService;

    @Autowired
    private MockMvc restVisitanteMockMvc;

    private Visitante visitante;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visitante createEntity() {
        Visitante visitante = new Visitante()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .idCelular(DEFAULT_ID_CELULAR)
            .data(DEFAULT_DATA);
        return visitante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visitante createUpdatedEntity() {
        Visitante visitante = new Visitante()
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .idCelular(UPDATED_ID_CELULAR)
            .data(UPDATED_DATA);
        return visitante;
    }

    @BeforeEach
    public void initTest() {
        visitanteRepository.deleteAll();
        visitante = createEntity();
    }

    @Test
    public void createVisitante() throws Exception {
        int databaseSizeBeforeCreate = visitanteRepository.findAll().size();

        // Create the Visitante
        restVisitanteMockMvc.perform(post("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitante)))
            .andExpect(status().isCreated());

        // Validate the Visitante in the database
        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeCreate + 1);
        Visitante testVisitante = visitanteList.get(visitanteList.size() - 1);
        assertThat(testVisitante.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testVisitante.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVisitante.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testVisitante.getIdCelular()).isEqualTo(DEFAULT_ID_CELULAR);
        assertThat(testVisitante.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    public void createVisitanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitanteRepository.findAll().size();

        // Create the Visitante with an existing ID
        visitante.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitanteMockMvc.perform(post("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitante)))
            .andExpect(status().isBadRequest());

        // Validate the Visitante in the database
        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitanteRepository.findAll().size();
        // set the field null
        visitante.setNome(null);

        // Create the Visitante, which fails.

        restVisitanteMockMvc.perform(post("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitante)))
            .andExpect(status().isBadRequest());

        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIdCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitanteRepository.findAll().size();
        // set the field null
        visitante.setIdCelular(null);

        // Create the Visitante, which fails.

        restVisitanteMockMvc.perform(post("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitante)))
            .andExpect(status().isBadRequest());

        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitanteRepository.findAll().size();
        // set the field null
        visitante.setData(null);

        // Create the Visitante, which fails.

        restVisitanteMockMvc.perform(post("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitante)))
            .andExpect(status().isBadRequest());

        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVisitantes() throws Exception {
        // Initialize the database
        visitanteRepository.save(visitante);

        // Get all the visitanteList
        restVisitanteMockMvc.perform(get("/api/visitantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visitante.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].idCelular").value(hasItem(DEFAULT_ID_CELULAR)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    public void getVisitante() throws Exception {
        // Initialize the database
        visitanteRepository.save(visitante);

        // Get the visitante
        restVisitanteMockMvc.perform(get("/api/visitantes/{id}", visitante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visitante.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.idCelular").value(DEFAULT_ID_CELULAR))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }

    @Test
    public void getNonExistingVisitante() throws Exception {
        // Get the visitante
        restVisitanteMockMvc.perform(get("/api/visitantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVisitante() throws Exception {
        // Initialize the database
        visitanteService.save(visitante);

        int databaseSizeBeforeUpdate = visitanteRepository.findAll().size();

        // Update the visitante
        Visitante updatedVisitante = visitanteRepository.findById(visitante.getId()).get();
        updatedVisitante
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .idCelular(UPDATED_ID_CELULAR)
            .data(UPDATED_DATA);

        restVisitanteMockMvc.perform(put("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisitante)))
            .andExpect(status().isOk());

        // Validate the Visitante in the database
        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeUpdate);
        Visitante testVisitante = visitanteList.get(visitanteList.size() - 1);
        assertThat(testVisitante.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testVisitante.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVisitante.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testVisitante.getIdCelular()).isEqualTo(UPDATED_ID_CELULAR);
        assertThat(testVisitante.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    public void updateNonExistingVisitante() throws Exception {
        int databaseSizeBeforeUpdate = visitanteRepository.findAll().size();

        // Create the Visitante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitanteMockMvc.perform(put("/api/visitantes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visitante)))
            .andExpect(status().isBadRequest());

        // Validate the Visitante in the database
        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVisitante() throws Exception {
        // Initialize the database
        visitanteService.save(visitante);

        int databaseSizeBeforeDelete = visitanteRepository.findAll().size();

        // Delete the visitante
        restVisitanteMockMvc.perform(delete("/api/visitantes/{id}", visitante.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visitante> visitanteList = visitanteRepository.findAll();
        assertThat(visitanteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
