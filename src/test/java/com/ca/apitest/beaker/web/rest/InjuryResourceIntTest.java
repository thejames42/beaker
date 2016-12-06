package com.ca.apitest.beaker.web.rest;

import com.ca.apitest.beaker.BeakerApp;

import com.ca.apitest.beaker.domain.Injury;
import com.ca.apitest.beaker.repository.InjuryRepository;
import com.ca.apitest.beaker.service.InjuryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ca.apitest.beaker.domain.enumeration.Classification;
/**
 * Test class for the InjuryResource REST controller.
 *
 * @see InjuryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeakerApp.class)
public class InjuryResourceIntTest {

    private static final Classification DEFAULT_CLASSIFICATION = Classification.burn;
    private static final Classification UPDATED_CLASSIFICATION = Classification.laceration;

    private static final Integer DEFAULT_SEVERITY = 1;
    private static final Integer UPDATED_SEVERITY = 2;

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INFLICTED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INFLICTED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_FATAL = false;
    private static final Boolean UPDATED_FATAL = true;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    @Inject
    private InjuryRepository injuryRepository;

    @Inject
    private InjuryService injuryService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restInjuryMockMvc;

    private Injury injury;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InjuryResource injuryResource = new InjuryResource();
        ReflectionTestUtils.setField(injuryResource, "injuryService", injuryService);
        this.restInjuryMockMvc = MockMvcBuilders.standaloneSetup(injuryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Injury createEntity() {
        Injury injury = new Injury()
                .classification(DEFAULT_CLASSIFICATION)
                .severity(DEFAULT_SEVERITY)
                .location(DEFAULT_LOCATION)
                .inflicted(DEFAULT_INFLICTED)
                .fatal(DEFAULT_FATAL)
                .source(DEFAULT_SOURCE);
        return injury;
    }

    @Before
    public void initTest() {
        injuryRepository.deleteAll();
        injury = createEntity();
    }

    @Test
    public void createInjury() throws Exception {
        int databaseSizeBeforeCreate = injuryRepository.findAll().size();

        // Create the Injury

        restInjuryMockMvc.perform(post("/api/injuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(injury)))
            .andExpect(status().isCreated());

        // Validate the Injury in the database
        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeCreate + 1);
        Injury testInjury = injuries.get(injuries.size() - 1);
        assertThat(testInjury.getClassification()).isEqualTo(DEFAULT_CLASSIFICATION);
        assertThat(testInjury.getSeverity()).isEqualTo(DEFAULT_SEVERITY);
        assertThat(testInjury.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testInjury.getInflicted()).isEqualTo(DEFAULT_INFLICTED);
        assertThat(testInjury.isFatal()).isEqualTo(DEFAULT_FATAL);
        assertThat(testInjury.getSource()).isEqualTo(DEFAULT_SOURCE);
    }

    @Test
    public void checkClassificationIsRequired() throws Exception {
        int databaseSizeBeforeTest = injuryRepository.findAll().size();
        // set the field null
        injury.setClassification(null);

        // Create the Injury, which fails.

        restInjuryMockMvc.perform(post("/api/injuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(injury)))
            .andExpect(status().isBadRequest());

        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkSeverityIsRequired() throws Exception {
        int databaseSizeBeforeTest = injuryRepository.findAll().size();
        // set the field null
        injury.setSeverity(null);

        // Create the Injury, which fails.

        restInjuryMockMvc.perform(post("/api/injuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(injury)))
            .andExpect(status().isBadRequest());

        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = injuryRepository.findAll().size();
        // set the field null
        injury.setLocation(null);

        // Create the Injury, which fails.

        restInjuryMockMvc.perform(post("/api/injuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(injury)))
            .andExpect(status().isBadRequest());

        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkInflictedIsRequired() throws Exception {
        int databaseSizeBeforeTest = injuryRepository.findAll().size();
        // set the field null
        injury.setInflicted(null);

        // Create the Injury, which fails.

        restInjuryMockMvc.perform(post("/api/injuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(injury)))
            .andExpect(status().isBadRequest());

        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllInjuries() throws Exception {
        // Initialize the database
        injuryRepository.save(injury);

        // Get all the injuries
        restInjuryMockMvc.perform(get("/api/injuries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(injury.getId())))
            .andExpect(jsonPath("$.[*].classification").value(hasItem(DEFAULT_CLASSIFICATION.toString())))
            .andExpect(jsonPath("$.[*].severity").value(hasItem(DEFAULT_SEVERITY)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].inflicted").value(hasItem(DEFAULT_INFLICTED.toString())))
            .andExpect(jsonPath("$.[*].fatal").value(hasItem(DEFAULT_FATAL.booleanValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())));
    }

    @Test
    public void getInjury() throws Exception {
        // Initialize the database
        injuryRepository.save(injury);

        // Get the injury
        restInjuryMockMvc.perform(get("/api/injuries/{id}", injury.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(injury.getId()))
            .andExpect(jsonPath("$.classification").value(DEFAULT_CLASSIFICATION.toString()))
            .andExpect(jsonPath("$.severity").value(DEFAULT_SEVERITY))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()))
            .andExpect(jsonPath("$.inflicted").value(DEFAULT_INFLICTED.toString()))
            .andExpect(jsonPath("$.fatal").value(DEFAULT_FATAL.booleanValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()));
    }

    @Test
    public void getNonExistingInjury() throws Exception {
        // Get the injury
        restInjuryMockMvc.perform(get("/api/injuries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInjury() throws Exception {
        // Initialize the database
        injuryService.save(injury);

        int databaseSizeBeforeUpdate = injuryRepository.findAll().size();

        // Update the injury
        Injury updatedInjury = injuryRepository.findOne(injury.getId());
        updatedInjury
                .classification(UPDATED_CLASSIFICATION)
                .severity(UPDATED_SEVERITY)
                .location(UPDATED_LOCATION)
                .inflicted(UPDATED_INFLICTED)
                .fatal(UPDATED_FATAL)
                .source(UPDATED_SOURCE);

        restInjuryMockMvc.perform(put("/api/injuries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInjury)))
            .andExpect(status().isOk());

        // Validate the Injury in the database
        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeUpdate);
        Injury testInjury = injuries.get(injuries.size() - 1);
        assertThat(testInjury.getClassification()).isEqualTo(UPDATED_CLASSIFICATION);
        assertThat(testInjury.getSeverity()).isEqualTo(UPDATED_SEVERITY);
        assertThat(testInjury.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testInjury.getInflicted()).isEqualTo(UPDATED_INFLICTED);
        assertThat(testInjury.isFatal()).isEqualTo(UPDATED_FATAL);
        assertThat(testInjury.getSource()).isEqualTo(UPDATED_SOURCE);
    }

    @Test
    public void deleteInjury() throws Exception {
        // Initialize the database
        injuryService.save(injury);

        int databaseSizeBeforeDelete = injuryRepository.findAll().size();

        // Get the injury
        restInjuryMockMvc.perform(delete("/api/injuries/{id}", injury.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Injury> injuries = injuryRepository.findAll();
        assertThat(injuries).hasSize(databaseSizeBeforeDelete - 1);
    }
}
