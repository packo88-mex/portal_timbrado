package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TipoRecibo;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.TipoReciboRepository;
import com.mycompany.myapp.service.dto.TipoReciboDTO;
import com.mycompany.myapp.service.mapper.TipoReciboMapper;
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
 * Integration tests for the {@link TipoReciboResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoReciboResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIPO_RECIBO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_RECIBO = "BBBBBBBBBB";

    private static final String DEFAULT_CLAVE = "AAAAAAAAAA";
    private static final String UPDATED_CLAVE = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-recibos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoReciboRepository tipoReciboRepository;

    @Autowired
    private TipoReciboMapper tipoReciboMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoReciboMockMvc;

    private TipoRecibo tipoRecibo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecibo createEntity(EntityManager em) {
        TipoRecibo tipoRecibo = new TipoRecibo()
            .fecha(DEFAULT_FECHA)
            .tipoRecibo(DEFAULT_TIPO_RECIBO)
            .clave(DEFAULT_CLAVE)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return tipoRecibo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoRecibo createUpdatedEntity(EntityManager em) {
        TipoRecibo tipoRecibo = new TipoRecibo()
            .fecha(UPDATED_FECHA)
            .tipoRecibo(UPDATED_TIPO_RECIBO)
            .clave(UPDATED_CLAVE)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return tipoRecibo;
    }

    @BeforeEach
    public void initTest() {
        tipoRecibo = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoRecibo() throws Exception {
        int databaseSizeBeforeCreate = tipoReciboRepository.findAll().size();
        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);
        restTipoReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeCreate + 1);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(DEFAULT_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoRecibo.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createTipoReciboWithExistingId() throws Exception {
        // Create the TipoRecibo with an existing ID
        tipoRecibo.setId(1L);
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        int databaseSizeBeforeCreate = tipoReciboRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoReciboMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoRecibos() throws Exception {
        // Initialize the database
        tipoReciboRepository.saveAndFlush(tipoRecibo);

        // Get all the tipoReciboList
        restTipoReciboMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoRecibo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].tipoRecibo").value(hasItem(DEFAULT_TIPO_RECIBO)))
            .andExpect(jsonPath("$.[*].clave").value(hasItem(DEFAULT_CLAVE)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getTipoRecibo() throws Exception {
        // Initialize the database
        tipoReciboRepository.saveAndFlush(tipoRecibo);

        // Get the tipoRecibo
        restTipoReciboMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoRecibo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoRecibo.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.tipoRecibo").value(DEFAULT_TIPO_RECIBO))
            .andExpect(jsonPath("$.clave").value(DEFAULT_CLAVE))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingTipoRecibo() throws Exception {
        // Get the tipoRecibo
        restTipoReciboMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoRecibo() throws Exception {
        // Initialize the database
        tipoReciboRepository.saveAndFlush(tipoRecibo);

        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();

        // Update the tipoRecibo
        TipoRecibo updatedTipoRecibo = tipoReciboRepository.findById(tipoRecibo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoRecibo are not directly saved in db
        em.detach(updatedTipoRecibo);
        updatedTipoRecibo
            .fecha(UPDATED_FECHA)
            .tipoRecibo(UPDATED_TIPO_RECIBO)
            .clave(UPDATED_CLAVE)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(updatedTipoRecibo);

        restTipoReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoReciboDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(UPDATED_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoRecibo.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();
        tipoRecibo.setId(count.incrementAndGet());

        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoReciboDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();
        tipoRecibo.setId(count.incrementAndGet());

        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReciboMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();
        tipoRecibo.setId(count.incrementAndGet());

        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReciboMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoReciboWithPatch() throws Exception {
        // Initialize the database
        tipoReciboRepository.saveAndFlush(tipoRecibo);

        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();

        // Update the tipoRecibo using partial update
        TipoRecibo partialUpdatedTipoRecibo = new TipoRecibo();
        partialUpdatedTipoRecibo.setId(tipoRecibo.getId());

        partialUpdatedTipoRecibo.tipoRecibo(UPDATED_TIPO_RECIBO);

        restTipoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRecibo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRecibo))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(UPDATED_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(DEFAULT_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoRecibo.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateTipoReciboWithPatch() throws Exception {
        // Initialize the database
        tipoReciboRepository.saveAndFlush(tipoRecibo);

        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();

        // Update the tipoRecibo using partial update
        TipoRecibo partialUpdatedTipoRecibo = new TipoRecibo();
        partialUpdatedTipoRecibo.setId(tipoRecibo.getId());

        partialUpdatedTipoRecibo
            .fecha(UPDATED_FECHA)
            .tipoRecibo(UPDATED_TIPO_RECIBO)
            .clave(UPDATED_CLAVE)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restTipoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoRecibo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoRecibo))
            )
            .andExpect(status().isOk());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
        TipoRecibo testTipoRecibo = tipoReciboList.get(tipoReciboList.size() - 1);
        assertThat(testTipoRecibo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoRecibo.getTipoRecibo()).isEqualTo(UPDATED_TIPO_RECIBO);
        assertThat(testTipoRecibo.getClave()).isEqualTo(UPDATED_CLAVE);
        assertThat(testTipoRecibo.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoRecibo.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testTipoRecibo.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();
        tipoRecibo.setId(count.incrementAndGet());

        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoReciboDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();
        tipoRecibo.setId(count.incrementAndGet());

        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoRecibo() throws Exception {
        int databaseSizeBeforeUpdate = tipoReciboRepository.findAll().size();
        tipoRecibo.setId(count.incrementAndGet());

        // Create the TipoRecibo
        TipoReciboDTO tipoReciboDTO = tipoReciboMapper.toDto(tipoRecibo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoReciboMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoReciboDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoRecibo in the database
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoRecibo() throws Exception {
        // Initialize the database
        tipoReciboRepository.saveAndFlush(tipoRecibo);

        int databaseSizeBeforeDelete = tipoReciboRepository.findAll().size();

        // Delete the tipoRecibo
        restTipoReciboMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoRecibo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoRecibo> tipoReciboList = tipoReciboRepository.findAll();
        assertThat(tipoReciboList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
