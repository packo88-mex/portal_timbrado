package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TipoCfdi;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.TipoCfdiRepository;
import com.mycompany.myapp.service.dto.TipoCfdiDTO;
import com.mycompany.myapp.service.mapper.TipoCfdiMapper;
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
 * Integration tests for the {@link TipoCfdiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TipoCfdiResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TIPO_CFDI = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_CFDI = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tipo-cfdis";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TipoCfdiRepository tipoCfdiRepository;

    @Autowired
    private TipoCfdiMapper tipoCfdiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoCfdiMockMvc;

    private TipoCfdi tipoCfdi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCfdi createEntity(EntityManager em) {
        TipoCfdi tipoCfdi = new TipoCfdi()
            .fecha(DEFAULT_FECHA)
            .tipoCfdi(DEFAULT_TIPO_CFDI)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return tipoCfdi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoCfdi createUpdatedEntity(EntityManager em) {
        TipoCfdi tipoCfdi = new TipoCfdi()
            .fecha(UPDATED_FECHA)
            .tipoCfdi(UPDATED_TIPO_CFDI)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return tipoCfdi;
    }

    @BeforeEach
    public void initTest() {
        tipoCfdi = createEntity(em);
    }

    @Test
    @Transactional
    void createTipoCfdi() throws Exception {
        int databaseSizeBeforeCreate = tipoCfdiRepository.findAll().size();
        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);
        restTipoCfdiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeCreate + 1);
        TipoCfdi testTipoCfdi = tipoCfdiList.get(tipoCfdiList.size() - 1);
        assertThat(testTipoCfdi.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testTipoCfdi.getTipoCfdi()).isEqualTo(DEFAULT_TIPO_CFDI);
        assertThat(testTipoCfdi.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoCfdi.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testTipoCfdi.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createTipoCfdiWithExistingId() throws Exception {
        // Create the TipoCfdi with an existing ID
        tipoCfdi.setId(1L);
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        int databaseSizeBeforeCreate = tipoCfdiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoCfdiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTipoCfdis() throws Exception {
        // Initialize the database
        tipoCfdiRepository.saveAndFlush(tipoCfdi);

        // Get all the tipoCfdiList
        restTipoCfdiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoCfdi.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].tipoCfdi").value(hasItem(DEFAULT_TIPO_CFDI)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getTipoCfdi() throws Exception {
        // Initialize the database
        tipoCfdiRepository.saveAndFlush(tipoCfdi);

        // Get the tipoCfdi
        restTipoCfdiMockMvc
            .perform(get(ENTITY_API_URL_ID, tipoCfdi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoCfdi.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.tipoCfdi").value(DEFAULT_TIPO_CFDI))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingTipoCfdi() throws Exception {
        // Get the tipoCfdi
        restTipoCfdiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTipoCfdi() throws Exception {
        // Initialize the database
        tipoCfdiRepository.saveAndFlush(tipoCfdi);

        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();

        // Update the tipoCfdi
        TipoCfdi updatedTipoCfdi = tipoCfdiRepository.findById(tipoCfdi.getId()).get();
        // Disconnect from session so that the updates on updatedTipoCfdi are not directly saved in db
        em.detach(updatedTipoCfdi);
        updatedTipoCfdi
            .fecha(UPDATED_FECHA)
            .tipoCfdi(UPDATED_TIPO_CFDI)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(updatedTipoCfdi);

        restTipoCfdiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoCfdiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO))
            )
            .andExpect(status().isOk());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
        TipoCfdi testTipoCfdi = tipoCfdiList.get(tipoCfdiList.size() - 1);
        assertThat(testTipoCfdi.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoCfdi.getTipoCfdi()).isEqualTo(UPDATED_TIPO_CFDI);
        assertThat(testTipoCfdi.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoCfdi.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testTipoCfdi.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingTipoCfdi() throws Exception {
        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();
        tipoCfdi.setId(count.incrementAndGet());

        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoCfdiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tipoCfdiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTipoCfdi() throws Exception {
        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();
        tipoCfdi.setId(count.incrementAndGet());

        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoCfdiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTipoCfdi() throws Exception {
        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();
        tipoCfdi.setId(count.incrementAndGet());

        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoCfdiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTipoCfdiWithPatch() throws Exception {
        // Initialize the database
        tipoCfdiRepository.saveAndFlush(tipoCfdi);

        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();

        // Update the tipoCfdi using partial update
        TipoCfdi partialUpdatedTipoCfdi = new TipoCfdi();
        partialUpdatedTipoCfdi.setId(tipoCfdi.getId());

        partialUpdatedTipoCfdi.fecha(UPDATED_FECHA);

        restTipoCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoCfdi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCfdi))
            )
            .andExpect(status().isOk());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
        TipoCfdi testTipoCfdi = tipoCfdiList.get(tipoCfdiList.size() - 1);
        assertThat(testTipoCfdi.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoCfdi.getTipoCfdi()).isEqualTo(DEFAULT_TIPO_CFDI);
        assertThat(testTipoCfdi.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testTipoCfdi.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testTipoCfdi.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateTipoCfdiWithPatch() throws Exception {
        // Initialize the database
        tipoCfdiRepository.saveAndFlush(tipoCfdi);

        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();

        // Update the tipoCfdi using partial update
        TipoCfdi partialUpdatedTipoCfdi = new TipoCfdi();
        partialUpdatedTipoCfdi.setId(tipoCfdi.getId());

        partialUpdatedTipoCfdi
            .fecha(UPDATED_FECHA)
            .tipoCfdi(UPDATED_TIPO_CFDI)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restTipoCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTipoCfdi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTipoCfdi))
            )
            .andExpect(status().isOk());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
        TipoCfdi testTipoCfdi = tipoCfdiList.get(tipoCfdiList.size() - 1);
        assertThat(testTipoCfdi.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testTipoCfdi.getTipoCfdi()).isEqualTo(UPDATED_TIPO_CFDI);
        assertThat(testTipoCfdi.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testTipoCfdi.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testTipoCfdi.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingTipoCfdi() throws Exception {
        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();
        tipoCfdi.setId(count.incrementAndGet());

        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tipoCfdiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTipoCfdi() throws Exception {
        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();
        tipoCfdi.setId(count.incrementAndGet());

        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTipoCfdi() throws Exception {
        int databaseSizeBeforeUpdate = tipoCfdiRepository.findAll().size();
        tipoCfdi.setId(count.incrementAndGet());

        // Create the TipoCfdi
        TipoCfdiDTO tipoCfdiDTO = tipoCfdiMapper.toDto(tipoCfdi);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTipoCfdiMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tipoCfdiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TipoCfdi in the database
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTipoCfdi() throws Exception {
        // Initialize the database
        tipoCfdiRepository.saveAndFlush(tipoCfdi);

        int databaseSizeBeforeDelete = tipoCfdiRepository.findAll().size();

        // Delete the tipoCfdi
        restTipoCfdiMockMvc
            .perform(delete(ENTITY_API_URL_ID, tipoCfdi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoCfdi> tipoCfdiList = tipoCfdiRepository.findAll();
        assertThat(tipoCfdiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
