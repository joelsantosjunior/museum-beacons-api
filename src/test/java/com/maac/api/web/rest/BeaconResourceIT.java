package com.maac.api.web.rest;

import com.maac.api.MaacBeaconApiApp;
import com.maac.api.domain.Beacon;
import com.maac.api.repository.BeaconRepository;
import com.maac.api.service.BeaconService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.maac.api.domain.enumeration.TipoConteudo;
/**
 * Integration tests for the {@link BeaconResource} REST controller.
 */
@SpringBootTest(classes = MaacBeaconApiApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BeaconResourceIT {

    private static final String DEFAULT_LOCAL = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL = "BBBBBBBBBB";

    private static final String DEFAULT_ID_BEACON = "AAAAAAAAAA";
    private static final String UPDATED_ID_BEACON = "BBBBBBBBBB";

    private static final TipoConteudo DEFAULT_TIPO_CONTEUDO = TipoConteudo.TEXTO;
    private static final TipoConteudo UPDATED_TIPO_CONTEUDO = TipoConteudo.IMAGEM;

    private static final String DEFAULT_CONTEUDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTEUDO = "BBBBBBBBBB";

    private static final String DEFAULT_LEGENDA = "AAAAAAAAAA";
    private static final String UPDATED_LEGENDA = "BBBBBBBBBB";

    @Autowired
    private BeaconRepository beaconRepository;

    @Autowired
    private BeaconService beaconService;

    @Autowired
    private MockMvc restBeaconMockMvc;

    private Beacon beacon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beacon createEntity() {
        Beacon beacon = new Beacon()
            .local(DEFAULT_LOCAL)
            .idBeacon(DEFAULT_ID_BEACON)
            .tipoConteudo(DEFAULT_TIPO_CONTEUDO)
            .conteudo(DEFAULT_CONTEUDO)
            .legenda(DEFAULT_LEGENDA);
        return beacon;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Beacon createUpdatedEntity() {
        Beacon beacon = new Beacon()
            .local(UPDATED_LOCAL)
            .idBeacon(UPDATED_ID_BEACON)
            .tipoConteudo(UPDATED_TIPO_CONTEUDO)
            .conteudo(UPDATED_CONTEUDO)
            .legenda(UPDATED_LEGENDA);
        return beacon;
    }

    @BeforeEach
    public void initTest() {
        beaconRepository.deleteAll();
        beacon = createEntity();
    }

    @Test
    public void createBeacon() throws Exception {
        int databaseSizeBeforeCreate = beaconRepository.findAll().size();

        // Create the Beacon
        restBeaconMockMvc.perform(post("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isCreated());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeCreate + 1);
        Beacon testBeacon = beaconList.get(beaconList.size() - 1);
        assertThat(testBeacon.getLocal()).isEqualTo(DEFAULT_LOCAL);
        assertThat(testBeacon.getIdBeacon()).isEqualTo(DEFAULT_ID_BEACON);
        assertThat(testBeacon.getTipoConteudo()).isEqualTo(DEFAULT_TIPO_CONTEUDO);
        assertThat(testBeacon.getConteudo()).isEqualTo(DEFAULT_CONTEUDO);
        assertThat(testBeacon.getLegenda()).isEqualTo(DEFAULT_LEGENDA);
    }

    @Test
    public void createBeaconWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = beaconRepository.findAll().size();

        // Create the Beacon with an existing ID
        beacon.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBeaconMockMvc.perform(post("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isBadRequest());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void checkLocalIsRequired() throws Exception {
        int databaseSizeBeforeTest = beaconRepository.findAll().size();
        // set the field null
        beacon.setLocal(null);

        // Create the Beacon, which fails.

        restBeaconMockMvc.perform(post("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isBadRequest());

        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkIdBeaconIsRequired() throws Exception {
        int databaseSizeBeforeTest = beaconRepository.findAll().size();
        // set the field null
        beacon.setIdBeacon(null);

        // Create the Beacon, which fails.

        restBeaconMockMvc.perform(post("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isBadRequest());

        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllBeacons() throws Exception {
        // Initialize the database
        beaconRepository.save(beacon);

        // Get all the beaconList
        restBeaconMockMvc.perform(get("/api/beacons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(beacon.getId())))
            .andExpect(jsonPath("$.[*].local").value(hasItem(DEFAULT_LOCAL)))
            .andExpect(jsonPath("$.[*].idBeacon").value(hasItem(DEFAULT_ID_BEACON)))
            .andExpect(jsonPath("$.[*].tipoConteudo").value(hasItem(DEFAULT_TIPO_CONTEUDO.toString())))
            .andExpect(jsonPath("$.[*].conteudo").value(hasItem(DEFAULT_CONTEUDO)))
            .andExpect(jsonPath("$.[*].legenda").value(hasItem(DEFAULT_LEGENDA)));
    }
    
    @Test
    public void getBeacon() throws Exception {
        // Initialize the database
        beaconRepository.save(beacon);

        // Get the beacon
        restBeaconMockMvc.perform(get("/api/beacons/{id}", beacon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(beacon.getId()))
            .andExpect(jsonPath("$.local").value(DEFAULT_LOCAL))
            .andExpect(jsonPath("$.idBeacon").value(DEFAULT_ID_BEACON))
            .andExpect(jsonPath("$.tipoConteudo").value(DEFAULT_TIPO_CONTEUDO.toString()))
            .andExpect(jsonPath("$.conteudo").value(DEFAULT_CONTEUDO))
            .andExpect(jsonPath("$.legenda").value(DEFAULT_LEGENDA));
    }

    @Test
    public void getNonExistingBeacon() throws Exception {
        // Get the beacon
        restBeaconMockMvc.perform(get("/api/beacons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBeacon() throws Exception {
        // Initialize the database
        beaconService.save(beacon);

        int databaseSizeBeforeUpdate = beaconRepository.findAll().size();

        // Update the beacon
        Beacon updatedBeacon = beaconRepository.findById(beacon.getId()).get();
        updatedBeacon
            .local(UPDATED_LOCAL)
            .idBeacon(UPDATED_ID_BEACON)
            .tipoConteudo(UPDATED_TIPO_CONTEUDO)
            .conteudo(UPDATED_CONTEUDO)
            .legenda(UPDATED_LEGENDA);

        restBeaconMockMvc.perform(put("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBeacon)))
            .andExpect(status().isOk());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeUpdate);
        Beacon testBeacon = beaconList.get(beaconList.size() - 1);
        assertThat(testBeacon.getLocal()).isEqualTo(UPDATED_LOCAL);
        assertThat(testBeacon.getIdBeacon()).isEqualTo(UPDATED_ID_BEACON);
        assertThat(testBeacon.getTipoConteudo()).isEqualTo(UPDATED_TIPO_CONTEUDO);
        assertThat(testBeacon.getConteudo()).isEqualTo(UPDATED_CONTEUDO);
        assertThat(testBeacon.getLegenda()).isEqualTo(UPDATED_LEGENDA);
    }

    @Test
    public void updateNonExistingBeacon() throws Exception {
        int databaseSizeBeforeUpdate = beaconRepository.findAll().size();

        // Create the Beacon

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBeaconMockMvc.perform(put("/api/beacons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(beacon)))
            .andExpect(status().isBadRequest());

        // Validate the Beacon in the database
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteBeacon() throws Exception {
        // Initialize the database
        beaconService.save(beacon);

        int databaseSizeBeforeDelete = beaconRepository.findAll().size();

        // Delete the beacon
        restBeaconMockMvc.perform(delete("/api/beacons/{id}", beacon.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Beacon> beaconList = beaconRepository.findAll();
        assertThat(beaconList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
