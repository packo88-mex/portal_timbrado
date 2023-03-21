package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.EstatusCfdi;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.EstatusCfdiRepository;
import com.mycompany.myapp.service.dto.EstatusCfdiDTO;
import com.mycompany.myapp.service.mapper.EstatusCfdiMapper;
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
 * Integration tests for the {@link EstatusCfdiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstatusCfdiResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPCION_ESTATUS_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_ESTATUS_CFDI = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estatus-cfdis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstatusCfdiRepository estatusCfdiRepository;

    @Autowired
    private EstatusCfdiMapper estatusCfdiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstatusCfdiMockMvc;

    private EstatusCfdi estatusCfdi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusCfdi createEntity(EntityManager em) {
        EstatusCfdi estatusCfdi = new EstatusCfdi()
            .fecha(DEFAULT_FECHA)
            .descripcionEstatusCfdi(DEFAULT_DESCRIPCION_ESTATUS_CFDI)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return estatusCfdi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstatusCfdi createUpdatedEntity(EntityManager em) {
        EstatusCfdi estatusCfdi = new EstatusCfdi()
            .fecha(UPDATED_FECHA)
            .descripcionEstatusCfdi(UPDATED_DESCRIPCION_ESTATUS_CFDI)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return estatusCfdi;
    }

    @BeforeEach
    public void initTest() {
        estatusCfdi = createEntity(em);
    }

    @Test
    @Transactional
    void createEstatusCfdi() throws Exception {
        int databaseSizeBeforeCreate = estatusCfdiRepository.findAll().size();
        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);
        restEstatusCfdiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeCreate + 1);
        EstatusCfdi testEstatusCfdi = estatusCfdiList.get(estatusCfdiList.size() - 1);
        assertThat(testEstatusCfdi.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstatusCfdi.getDescripcionEstatusCfdi()).isEqualTo(DEFAULT_DESCRIPCION_ESTATUS_CFDI);
        assertThat(testEstatusCfdi.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstatusCfdi.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testEstatusCfdi.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createEstatusCfdiWithExistingId() throws Exception {
        // Create the EstatusCfdi with an existing ID
        estatusCfdi.setId(1L);
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        int databaseSizeBeforeCreate = estatusCfdiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstatusCfdiMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstatusCfdis() throws Exception {
        // Initialize the database
        estatusCfdiRepository.saveAndFlush(estatusCfdi);

        // Get all the estatusCfdiList
        restEstatusCfdiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estatusCfdi.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].descripcionEstatusCfdi").value(hasItem(DEFAULT_DESCRIPCION_ESTATUS_CFDI)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getEstatusCfdi() throws Exception {
        // Initialize the database
        estatusCfdiRepository.saveAndFlush(estatusCfdi);

        // Get the estatusCfdi
        restEstatusCfdiMockMvc
            .perform(get(ENTITY_API_URL_ID, estatusCfdi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estatusCfdi.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.descripcionEstatusCfdi").value(DEFAULT_DESCRIPCION_ESTATUS_CFDI))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingEstatusCfdi() throws Exception {
        // Get the estatusCfdi
        restEstatusCfdiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstatusCfdi() throws Exception {
        // Initialize the database
        estatusCfdiRepository.saveAndFlush(estatusCfdi);

        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();

        // Update the estatusCfdi
        EstatusCfdi updatedEstatusCfdi = estatusCfdiRepository.findById(estatusCfdi.getId()).get();
        // Disconnect from session so that the updates on updatedEstatusCfdi are not directly saved in db
        em.detach(updatedEstatusCfdi);
        updatedEstatusCfdi
            .fecha(UPDATED_FECHA)
            .descripcionEstatusCfdi(UPDATED_DESCRIPCION_ESTATUS_CFDI)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(updatedEstatusCfdi);

        restEstatusCfdiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estatusCfdiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
        EstatusCfdi testEstatusCfdi = estatusCfdiList.get(estatusCfdiList.size() - 1);
        assertThat(testEstatusCfdi.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstatusCfdi.getDescripcionEstatusCfdi()).isEqualTo(UPDATED_DESCRIPCION_ESTATUS_CFDI);
        assertThat(testEstatusCfdi.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstatusCfdi.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testEstatusCfdi.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingEstatusCfdi() throws Exception {
        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();
        estatusCfdi.setId(count.incrementAndGet());

        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusCfdiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estatusCfdiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstatusCfdi() throws Exception {
        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();
        estatusCfdi.setId(count.incrementAndGet());

        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusCfdiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstatusCfdi() throws Exception {
        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();
        estatusCfdi.setId(count.incrementAndGet());

        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusCfdiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstatusCfdiWithPatch() throws Exception {
        // Initialize the database
        estatusCfdiRepository.saveAndFlush(estatusCfdi);

        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();

        // Update the estatusCfdi using partial update
        EstatusCfdi partialUpdatedEstatusCfdi = new EstatusCfdi();
        partialUpdatedEstatusCfdi.setId(estatusCfdi.getId());

        partialUpdatedEstatusCfdi
            .fecha(UPDATED_FECHA)
            .descripcionEstatusCfdi(UPDATED_DESCRIPCION_ESTATUS_CFDI)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION);

        restEstatusCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstatusCfdi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstatusCfdi))
            )
            .andExpect(status().isOk());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
        EstatusCfdi testEstatusCfdi = estatusCfdiList.get(estatusCfdiList.size() - 1);
        assertThat(testEstatusCfdi.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstatusCfdi.getDescripcionEstatusCfdi()).isEqualTo(UPDATED_DESCRIPCION_ESTATUS_CFDI);
        assertThat(testEstatusCfdi.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstatusCfdi.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testEstatusCfdi.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateEstatusCfdiWithPatch() throws Exception {
        // Initialize the database
        estatusCfdiRepository.saveAndFlush(estatusCfdi);

        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();

        // Update the estatusCfdi using partial update
        EstatusCfdi partialUpdatedEstatusCfdi = new EstatusCfdi();
        partialUpdatedEstatusCfdi.setId(estatusCfdi.getId());

        partialUpdatedEstatusCfdi
            .fecha(UPDATED_FECHA)
            .descripcionEstatusCfdi(UPDATED_DESCRIPCION_ESTATUS_CFDI)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restEstatusCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstatusCfdi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstatusCfdi))
            )
            .andExpect(status().isOk());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
        EstatusCfdi testEstatusCfdi = estatusCfdiList.get(estatusCfdiList.size() - 1);
        assertThat(testEstatusCfdi.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstatusCfdi.getDescripcionEstatusCfdi()).isEqualTo(UPDATED_DESCRIPCION_ESTATUS_CFDI);
        assertThat(testEstatusCfdi.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstatusCfdi.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testEstatusCfdi.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingEstatusCfdi() throws Exception {
        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();
        estatusCfdi.setId(count.incrementAndGet());

        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstatusCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estatusCfdiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstatusCfdi() throws Exception {
        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();
        estatusCfdi.setId(count.incrementAndGet());

        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstatusCfdi() throws Exception {
        int databaseSizeBeforeUpdate = estatusCfdiRepository.findAll().size();
        estatusCfdi.setId(count.incrementAndGet());

        // Create the EstatusCfdi
        EstatusCfdiDTO estatusCfdiDTO = estatusCfdiMapper.toDto(estatusCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstatusCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estatusCfdiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstatusCfdi in the database
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstatusCfdi() throws Exception {
        // Initialize the database
        estatusCfdiRepository.saveAndFlush(estatusCfdi);

        int databaseSizeBeforeDelete = estatusCfdiRepository.findAll().size();

        // Delete the estatusCfdi
        restEstatusCfdiMockMvc
            .perform(delete(ENTITY_API_URL_ID, estatusCfdi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstatusCfdi> estatusCfdiList = estatusCfdiRepository.findAll();
        assertThat(estatusCfdiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
