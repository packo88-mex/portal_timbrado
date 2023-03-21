package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.RepresentacionGrafica;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.RepresentacionGraficaRepository;
import com.mycompany.myapp.service.dto.RepresentacionGraficaDTO;
import com.mycompany.myapp.service.mapper.RepresentacionGraficaMapper;
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
 * Integration tests for the {@link RepresentacionGraficaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RepresentacionGraficaResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_REPRESENTACION_GRAFICA = "AAAAAAAAAA";
    private static final String UPDATED_REPRESENTACION_GRAFICA = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/representacion-graficas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RepresentacionGraficaRepository representacionGraficaRepository;

    @Autowired
    private RepresentacionGraficaMapper representacionGraficaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRepresentacionGraficaMockMvc;

    private RepresentacionGrafica representacionGrafica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepresentacionGrafica createEntity(EntityManager em) {
        RepresentacionGrafica representacionGrafica = new RepresentacionGrafica()
            .fecha(DEFAULT_FECHA)
            .representacionGrafica(DEFAULT_REPRESENTACION_GRAFICA)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return representacionGrafica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RepresentacionGrafica createUpdatedEntity(EntityManager em) {
        RepresentacionGrafica representacionGrafica = new RepresentacionGrafica()
            .fecha(UPDATED_FECHA)
            .representacionGrafica(UPDATED_REPRESENTACION_GRAFICA)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return representacionGrafica;
    }

    @BeforeEach
    public void initTest() {
        representacionGrafica = createEntity(em);
    }

    @Test
    @Transactional
    void createRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeCreate = representacionGraficaRepository.findAll().size();
        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);
        restRepresentacionGraficaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeCreate + 1);
        RepresentacionGrafica testRepresentacionGrafica = representacionGraficaList.get(representacionGraficaList.size() - 1);
        assertThat(testRepresentacionGrafica.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRepresentacionGrafica.getRepresentacionGrafica()).isEqualTo(DEFAULT_REPRESENTACION_GRAFICA);
        assertThat(testRepresentacionGrafica.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRepresentacionGrafica.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testRepresentacionGrafica.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createRepresentacionGraficaWithExistingId() throws Exception {
        // Create the RepresentacionGrafica with an existing ID
        representacionGrafica.setId(1L);
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        int databaseSizeBeforeCreate = representacionGraficaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepresentacionGraficaMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRepresentacionGraficas() throws Exception {
        // Initialize the database
        representacionGraficaRepository.saveAndFlush(representacionGrafica);

        // Get all the representacionGraficaList
        restRepresentacionGraficaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representacionGrafica.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].representacionGrafica").value(hasItem(DEFAULT_REPRESENTACION_GRAFICA)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getRepresentacionGrafica() throws Exception {
        // Initialize the database
        representacionGraficaRepository.saveAndFlush(representacionGrafica);

        // Get the representacionGrafica
        restRepresentacionGraficaMockMvc
            .perform(get(ENTITY_API_URL_ID, representacionGrafica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(representacionGrafica.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.representacionGrafica").value(DEFAULT_REPRESENTACION_GRAFICA))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingRepresentacionGrafica() throws Exception {
        // Get the representacionGrafica
        restRepresentacionGraficaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRepresentacionGrafica() throws Exception {
        // Initialize the database
        representacionGraficaRepository.saveAndFlush(representacionGrafica);

        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();

        // Update the representacionGrafica
        RepresentacionGrafica updatedRepresentacionGrafica = representacionGraficaRepository.findById(representacionGrafica.getId()).get();
        // Disconnect from session so that the updates on updatedRepresentacionGrafica are not directly saved in db
        em.detach(updatedRepresentacionGrafica);
        updatedRepresentacionGrafica
            .fecha(UPDATED_FECHA)
            .representacionGrafica(UPDATED_REPRESENTACION_GRAFICA)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(updatedRepresentacionGrafica);

        restRepresentacionGraficaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, representacionGraficaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isOk());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
        RepresentacionGrafica testRepresentacionGrafica = representacionGraficaList.get(representacionGraficaList.size() - 1);
        assertThat(testRepresentacionGrafica.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRepresentacionGrafica.getRepresentacionGrafica()).isEqualTo(UPDATED_REPRESENTACION_GRAFICA);
        assertThat(testRepresentacionGrafica.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRepresentacionGrafica.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testRepresentacionGrafica.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();
        representacionGrafica.setId(count.incrementAndGet());

        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentacionGraficaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, representacionGraficaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();
        representacionGrafica.setId(count.incrementAndGet());

        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentacionGraficaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();
        representacionGrafica.setId(count.incrementAndGet());

        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentacionGraficaMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRepresentacionGraficaWithPatch() throws Exception {
        // Initialize the database
        representacionGraficaRepository.saveAndFlush(representacionGrafica);

        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();

        // Update the representacionGrafica using partial update
        RepresentacionGrafica partialUpdatedRepresentacionGrafica = new RepresentacionGrafica();
        partialUpdatedRepresentacionGrafica.setId(representacionGrafica.getId());

        partialUpdatedRepresentacionGrafica.fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restRepresentacionGraficaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentacionGrafica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentacionGrafica))
            )
            .andExpect(status().isOk());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
        RepresentacionGrafica testRepresentacionGrafica = representacionGraficaList.get(representacionGraficaList.size() - 1);
        assertThat(testRepresentacionGrafica.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testRepresentacionGrafica.getRepresentacionGrafica()).isEqualTo(DEFAULT_REPRESENTACION_GRAFICA);
        assertThat(testRepresentacionGrafica.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testRepresentacionGrafica.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testRepresentacionGrafica.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateRepresentacionGraficaWithPatch() throws Exception {
        // Initialize the database
        representacionGraficaRepository.saveAndFlush(representacionGrafica);

        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();

        // Update the representacionGrafica using partial update
        RepresentacionGrafica partialUpdatedRepresentacionGrafica = new RepresentacionGrafica();
        partialUpdatedRepresentacionGrafica.setId(representacionGrafica.getId());

        partialUpdatedRepresentacionGrafica
            .fecha(UPDATED_FECHA)
            .representacionGrafica(UPDATED_REPRESENTACION_GRAFICA)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restRepresentacionGraficaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRepresentacionGrafica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRepresentacionGrafica))
            )
            .andExpect(status().isOk());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
        RepresentacionGrafica testRepresentacionGrafica = representacionGraficaList.get(representacionGraficaList.size() - 1);
        assertThat(testRepresentacionGrafica.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testRepresentacionGrafica.getRepresentacionGrafica()).isEqualTo(UPDATED_REPRESENTACION_GRAFICA);
        assertThat(testRepresentacionGrafica.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testRepresentacionGrafica.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testRepresentacionGrafica.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();
        representacionGrafica.setId(count.incrementAndGet());

        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentacionGraficaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, representacionGraficaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();
        representacionGrafica.setId(count.incrementAndGet());

        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentacionGraficaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRepresentacionGrafica() throws Exception {
        int databaseSizeBeforeUpdate = representacionGraficaRepository.findAll().size();
        representacionGrafica.setId(count.incrementAndGet());

        // Create the RepresentacionGrafica
        RepresentacionGraficaDTO representacionGraficaDTO = representacionGraficaMapper.toDto(representacionGrafica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRepresentacionGraficaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(representacionGraficaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RepresentacionGrafica in the database
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRepresentacionGrafica() throws Exception {
        // Initialize the database
        representacionGraficaRepository.saveAndFlush(representacionGrafica);

        int databaseSizeBeforeDelete = representacionGraficaRepository.findAll().size();

        // Delete the representacionGrafica
        restRepresentacionGraficaMockMvc
            .perform(delete(ENTITY_API_URL_ID, representacionGrafica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RepresentacionGrafica> representacionGraficaList = representacionGraficaRepository.findAll();
        assertThat(representacionGraficaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
