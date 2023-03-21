package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RegimenFiscal;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.RegimenFiscalRepository;
import com.mycompany.myapp.service.dto.RegimenFiscalDTO;
import com.mycompany.myapp.service.mapper.RegimenFiscalMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RegimenFiscalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RegimenFiscalResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REGIMEN_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_REGIMEN_FISCAL = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/regimen-fiscals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RegimenFiscalRepository regimenFiscalRepository;

    @Autowired
    private RegimenFiscalMapper regimenFiscalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRegimenFiscalMockMvc;

    private RegimenFiscal regimenFiscal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegimenFiscal createEntity(EntityManager em) {
        RegimenFiscal regimenFiscal = new RegimenFiscal()
            .fecha(DEFAULT_FECHA)
            .regimenFiscal(DEFAULT_REGIMEN_FISCAL)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return regimenFiscal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegimenFiscal createUpdatedEntity(EntityManager em) {
        RegimenFiscal regimenFiscal = new RegimenFiscal()
            .fecha(UPDATED_FECHA)
            .regimenFiscal(UPDATED_REGIMEN_FISCAL)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return regimenFiscal;
    }

    @BeforeEach
    public void initTest() {
        regimenFiscal = createEntity(em);
    }

    @Test
    @Transactional
    void createRegimenFiscal() throws Exception {
        int databaseSizeBeforeCreate = regimenFiscalRepository.findAll().size();
        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);
        restRegimenFiscalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeCreate + 1);
        RegimenFiscal testRegimenFiscal = regimenFiscalList.get(regimenFiscalList.size() - 1);
        assertThat(testRegimenFiscal.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRegimenFiscal.getRegimenFiscal()).isEqualTo(DEFAULT_REGIMEN_FISCAL);
        assertThat(testRegimenFiscal.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRegimenFiscal.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testRegimenFiscal.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createRegimenFiscalWithExistingId() throws Exception {
        // Create the RegimenFiscal with an existing ID
        regimenFiscal.setId(1L);
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        int databaseSizeBeforeCreate = regimenFiscalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegimenFiscalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRegimenFiscals() throws Exception {
        // Initialize the database
        regimenFiscalRepository.saveAndFlush(regimenFiscal);

        // Get all the regimenFiscalList
        restRegimenFiscalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regimenFiscal.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].regimenFiscal").value(hasItem(DEFAULT_REGIMEN_FISCAL)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getRegimenFiscal() throws Exception {
        // Initialize the database
        regimenFiscalRepository.saveAndFlush(regimenFiscal);

        // Get the regimenFiscal
        restRegimenFiscalMockMvc
            .perform(get(ENTITY_API_URL_ID, regimenFiscal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(regimenFiscal.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.regimenFiscal").value(DEFAULT_REGIMEN_FISCAL))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingRegimenFiscal() throws Exception {
        // Get the regimenFiscal
        restRegimenFiscalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRegimenFiscal() throws Exception {
        // Initialize the database
        regimenFiscalRepository.saveAndFlush(regimenFiscal);

        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();

        // Update the regimenFiscal
        RegimenFiscal updatedRegimenFiscal = regimenFiscalRepository.findById(regimenFiscal.getId()).get();
        // Disconnect from session so that the updates on updatedRegimenFiscal are not directly saved in db
        em.detach(updatedRegimenFiscal);
        updatedRegimenFiscal
            .fecha(UPDATED_FECHA)
            .regimenFiscal(UPDATED_REGIMEN_FISCAL)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(updatedRegimenFiscal);

        restRegimenFiscalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regimenFiscalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isOk());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
        RegimenFiscal testRegimenFiscal = regimenFiscalList.get(regimenFiscalList.size() - 1);
        assertThat(testRegimenFiscal.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegimenFiscal.getRegimenFiscal()).isEqualTo(UPDATED_REGIMEN_FISCAL);
        assertThat(testRegimenFiscal.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegimenFiscal.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testRegimenFiscal.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingRegimenFiscal() throws Exception {
        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();
        regimenFiscal.setId(count.incrementAndGet());

        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegimenFiscalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, regimenFiscalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRegimenFiscal() throws Exception {
        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();
        regimenFiscal.setId(count.incrementAndGet());

        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegimenFiscalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRegimenFiscal() throws Exception {
        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();
        regimenFiscal.setId(count.incrementAndGet());

        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegimenFiscalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRegimenFiscalWithPatch() throws Exception {
        // Initialize the database
        regimenFiscalRepository.saveAndFlush(regimenFiscal);

        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();

        // Update the regimenFiscal using partial update
        RegimenFiscal partialUpdatedRegimenFiscal = new RegimenFiscal();
        partialUpdatedRegimenFiscal.setId(regimenFiscal.getId());

        partialUpdatedRegimenFiscal.fecha(UPDATED_FECHA).estatus(UPDATED_ESTATUS).usuario(UPDATED_USUARIO);

        restRegimenFiscalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegimenFiscal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegimenFiscal))
            )
            .andExpect(status().isOk());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
        RegimenFiscal testRegimenFiscal = regimenFiscalList.get(regimenFiscalList.size() - 1);
        assertThat(testRegimenFiscal.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegimenFiscal.getRegimenFiscal()).isEqualTo(DEFAULT_REGIMEN_FISCAL);
        assertThat(testRegimenFiscal.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegimenFiscal.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testRegimenFiscal.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateRegimenFiscalWithPatch() throws Exception {
        // Initialize the database
        regimenFiscalRepository.saveAndFlush(regimenFiscal);

        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();

        // Update the regimenFiscal using partial update
        RegimenFiscal partialUpdatedRegimenFiscal = new RegimenFiscal();
        partialUpdatedRegimenFiscal.setId(regimenFiscal.getId());

        partialUpdatedRegimenFiscal
            .fecha(UPDATED_FECHA)
            .regimenFiscal(UPDATED_REGIMEN_FISCAL)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restRegimenFiscalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRegimenFiscal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRegimenFiscal))
            )
            .andExpect(status().isOk());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
        RegimenFiscal testRegimenFiscal = regimenFiscalList.get(regimenFiscalList.size() - 1);
        assertThat(testRegimenFiscal.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRegimenFiscal.getRegimenFiscal()).isEqualTo(UPDATED_REGIMEN_FISCAL);
        assertThat(testRegimenFiscal.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRegimenFiscal.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testRegimenFiscal.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingRegimenFiscal() throws Exception {
        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();
        regimenFiscal.setId(count.incrementAndGet());

        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegimenFiscalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, regimenFiscalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRegimenFiscal() throws Exception {
        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();
        regimenFiscal.setId(count.incrementAndGet());

        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegimenFiscalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRegimenFiscal() throws Exception {
        int databaseSizeBeforeUpdate = regimenFiscalRepository.findAll().size();
        regimenFiscal.setId(count.incrementAndGet());

        // Create the RegimenFiscal
        RegimenFiscalDTO regimenFiscalDTO = regimenFiscalMapper.toDto(regimenFiscal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRegimenFiscalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(regimenFiscalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RegimenFiscal in the database
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRegimenFiscal() throws Exception {
        // Initialize the database
        regimenFiscalRepository.saveAndFlush(regimenFiscal);

        int databaseSizeBeforeDelete = regimenFiscalRepository.findAll().size();

        // Delete the regimenFiscal
        restRegimenFiscalMockMvc
            .perform(delete(ENTITY_API_URL_ID, regimenFiscal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegimenFiscal> regimenFiscalList = regimenFiscalRepository.findAll();
        assertThat(regimenFiscalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
