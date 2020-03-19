package com.maac.api.web.rest;

import com.maac.api.MaacBeaconApiApp;
import com.maac.api.domain.Visita;
import com.maac.api.repository.VisitaRepository;
import com.maac.api.service.VisitaService;

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
 * Integration tests for the {@link VisitaResource} REST controller.
 */
@SpringBootTest(classes = MaacBeaconApiApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class VisitaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "AAAAAAAAAA";
    private static final String UPDATED_CEP = "BBBBBBBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAAAAAAA";
    private static final String UPDATED_ENDERECO = "BBBBBBBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAAAAAAA";
    private static final String UPDATED_BAIRRO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_ID_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_ID_CELULAR = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private VisitaRepository visitaRepository;

    @Autowired
    private VisitaService visitaService;

    @Autowired
    private MockMvc restVisitaMockMvc;

    private Visita visita;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visita createEntity() {
        Visita visita = new Visita()
            .nome(DEFAULT_NOME)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .cep(DEFAULT_CEP)
            .endereco(DEFAULT_ENDERECO)
            .bairro(DEFAULT_BAIRRO)
            .complemento(DEFAULT_COMPLEMENTO)
            .idCelular(DEFAULT_ID_CELULAR)
            .data(DEFAULT_DATA);
        return visita;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Visita createUpdatedEntity() {
        Visita visita = new Visita()
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .cep(UPDATED_CEP)
            .endereco(UPDATED_ENDERECO)
            .bairro(UPDATED_BAIRRO)
            .complemento(UPDATED_COMPLEMENTO)
            .idCelular(UPDATED_ID_CELULAR)
            .data(UPDATED_DATA);
        return visita;
    }

    @BeforeEach
    public void initTest() {
        visitaRepository.deleteAll();
        visita = createEntity();
    }

    @Test
    public void createVisita() throws Exception {
        int databaseSizeBeforeCreate = visitaRepository.findAll().size();

        // Create the Visita
        restVisitaMockMvc.perform(post("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visita)))
            .andExpect(status().isCreated());

        // Validate the Visita in the database
        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeCreate + 1);
        Visita testVisita = visitaList.get(visitaList.size() - 1);
        assertThat(testVisita.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testVisita.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVisita.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testVisita.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testVisita.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testVisita.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testVisita.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
        assertThat(testVisita.getIdCelular()).isEqualTo(DEFAULT_ID_CELULAR);
        assertThat(testVisita.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    public void createVisitaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = visitaRepository.findAll().size();

        // Create the Visita with an existing ID
        visita.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restVisitaMockMvc.perform(post("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visita)))
            .andExpect(status().isBadRequest());

        // Validate the Visita in the database
        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitaRepository.findAll().size();
        // set the field null
        visita.setNome(null);

        // Create the Visita, which fails.

        restVisitaMockMvc.perform(post("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visita)))
            .andExpect(status().isBadRequest());

        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIdCelularIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitaRepository.findAll().size();
        // set the field null
        visita.setIdCelular(null);

        // Create the Visita, which fails.

        restVisitaMockMvc.perform(post("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visita)))
            .andExpect(status().isBadRequest());

        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = visitaRepository.findAll().size();
        // set the field null
        visita.setData(null);

        // Create the Visita, which fails.

        restVisitaMockMvc.perform(post("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visita)))
            .andExpect(status().isBadRequest());

        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllVisitas() throws Exception {
        // Initialize the database
        visitaRepository.save(visita);

        // Get all the visitaList
        restVisitaMockMvc.perform(get("/api/visitas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(visita.getId())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP)))
            .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO)))
            .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO)))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO)))
            .andExpect(jsonPath("$.[*].idCelular").value(hasItem(DEFAULT_ID_CELULAR)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(sameInstant(DEFAULT_DATA))));
    }
    
    @Test
    public void getVisita() throws Exception {
        // Initialize the database
        visitaRepository.save(visita);

        // Get the visita
        restVisitaMockMvc.perform(get("/api/visitas/{id}", visita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(visita.getId()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO))
            .andExpect(jsonPath("$.idCelular").value(DEFAULT_ID_CELULAR))
            .andExpect(jsonPath("$.data").value(sameInstant(DEFAULT_DATA)));
    }

    @Test
    public void getNonExistingVisita() throws Exception {
        // Get the visita
        restVisitaMockMvc.perform(get("/api/visitas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateVisita() throws Exception {
        // Initialize the database
        visitaService.save(visita);

        int databaseSizeBeforeUpdate = visitaRepository.findAll().size();

        // Update the visita
        Visita updatedVisita = visitaRepository.findById(visita.getId()).get();
        updatedVisita
            .nome(UPDATED_NOME)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .cep(UPDATED_CEP)
            .endereco(UPDATED_ENDERECO)
            .bairro(UPDATED_BAIRRO)
            .complemento(UPDATED_COMPLEMENTO)
            .idCelular(UPDATED_ID_CELULAR)
            .data(UPDATED_DATA);

        restVisitaMockMvc.perform(put("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedVisita)))
            .andExpect(status().isOk());

        // Validate the Visita in the database
        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeUpdate);
        Visita testVisita = visitaList.get(visitaList.size() - 1);
        assertThat(testVisita.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testVisita.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVisita.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testVisita.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testVisita.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testVisita.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testVisita.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
        assertThat(testVisita.getIdCelular()).isEqualTo(UPDATED_ID_CELULAR);
        assertThat(testVisita.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    public void updateNonExistingVisita() throws Exception {
        int databaseSizeBeforeUpdate = visitaRepository.findAll().size();

        // Create the Visita

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVisitaMockMvc.perform(put("/api/visitas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(visita)))
            .andExpect(status().isBadRequest());

        // Validate the Visita in the database
        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteVisita() throws Exception {
        // Initialize the database
        visitaService.save(visita);

        int databaseSizeBeforeDelete = visitaRepository.findAll().size();

        // Delete the visita
        restVisitaMockMvc.perform(delete("/api/visitas/{id}", visita.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Visita> visitaList = visitaRepository.findAll();
        assertThat(visitaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
