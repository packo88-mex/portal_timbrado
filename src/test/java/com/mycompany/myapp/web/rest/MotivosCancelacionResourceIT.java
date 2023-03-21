package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.MotivosCancelacion;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.MotivosCancelacionRepository;
import com.mycompany.myapp.service.dto.MotivosCancelacionDTO;
import com.mycompany.myapp.service.mapper.MotivosCancelacionMapper;
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
 * Integration tests for the {@link MotivosCancelacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MotivosCancelacionResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MOTIVO_CANCELACION = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO_CANCELACION = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/motivos-cancelacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MotivosCancelacionRepository motivosCancelacionRepository;

    @Autowired
    private MotivosCancelacionMapper motivosCancelacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMotivosCancelacionMockMvc;

    private MotivosCancelacion motivosCancelacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivosCancelacion createEntity(EntityManager em) {
        MotivosCancelacion motivosCancelacion = new MotivosCancelacion()
            .fecha(DEFAULT_FECHA)
            .motivoCancelacion(DEFAULT_MOTIVO_CANCELACION)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return motivosCancelacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MotivosCancelacion createUpdatedEntity(EntityManager em) {
        MotivosCancelacion motivosCancelacion = new MotivosCancelacion()
            .fecha(UPDATED_FECHA)
            .motivoCancelacion(UPDATED_MOTIVO_CANCELACION)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return motivosCancelacion;
    }

    @BeforeEach
    public void initTest() {
        motivosCancelacion = createEntity(em);
    }

    @Test
    @Transactional
    void createMotivosCancelacion() throws Exception {
        int databaseSizeBeforeCreate = motivosCancelacionRepository.findAll().size();
        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);
        restMotivosCancelacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeCreate + 1);
        MotivosCancelacion testMotivosCancelacion = motivosCancelacionList.get(motivosCancelacionList.size() - 1);
        assertThat(testMotivosCancelacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testMotivosCancelacion.getMotivoCancelacion()).isEqualTo(DEFAULT_MOTIVO_CANCELACION);
        assertThat(testMotivosCancelacion.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testMotivosCancelacion.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testMotivosCancelacion.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createMotivosCancelacionWithExistingId() throws Exception {
        // Create the MotivosCancelacion with an existing ID
        motivosCancelacion.setId(1L);
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        int databaseSizeBeforeCreate = motivosCancelacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMotivosCancelacionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMotivosCancelacions() throws Exception {
        // Initialize the database
        motivosCancelacionRepository.saveAndFlush(motivosCancelacion);

        // Get all the motivosCancelacionList
        restMotivosCancelacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(motivosCancelacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].motivoCancelacion").value(hasItem(DEFAULT_MOTIVO_CANCELACION)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getMotivosCancelacion() throws Exception {
        // Initialize the database
        motivosCancelacionRepository.saveAndFlush(motivosCancelacion);

        // Get the motivosCancelacion
        restMotivosCancelacionMockMvc
            .perform(get(ENTITY_API_URL_ID, motivosCancelacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(motivosCancelacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.motivoCancelacion").value(DEFAULT_MOTIVO_CANCELACION))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingMotivosCancelacion() throws Exception {
        // Get the motivosCancelacion
        restMotivosCancelacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMotivosCancelacion() throws Exception {
        // Initialize the database
        motivosCancelacionRepository.saveAndFlush(motivosCancelacion);

        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();

        // Update the motivosCancelacion
        MotivosCancelacion updatedMotivosCancelacion = motivosCancelacionRepository.findById(motivosCancelacion.getId()).get();
        // Disconnect from session so that the updates on updatedMotivosCancelacion are not directly saved in db
        em.detach(updatedMotivosCancelacion);
        updatedMotivosCancelacion
            .fecha(UPDATED_FECHA)
            .motivoCancelacion(UPDATED_MOTIVO_CANCELACION)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(updatedMotivosCancelacion);

        restMotivosCancelacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, motivosCancelacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
        MotivosCancelacion testMotivosCancelacion = motivosCancelacionList.get(motivosCancelacionList.size() - 1);
        assertThat(testMotivosCancelacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMotivosCancelacion.getMotivoCancelacion()).isEqualTo(UPDATED_MOTIVO_CANCELACION);
        assertThat(testMotivosCancelacion.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testMotivosCancelacion.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testMotivosCancelacion.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingMotivosCancelacion() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();
        motivosCancelacion.setId(count.incrementAndGet());

        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivosCancelacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, motivosCancelacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMotivosCancelacion() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();
        motivosCancelacion.setId(count.incrementAndGet());

        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivosCancelacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMotivosCancelacion() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();
        motivosCancelacion.setId(count.incrementAndGet());

        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivosCancelacionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMotivosCancelacionWithPatch() throws Exception {
        // Initialize the database
        motivosCancelacionRepository.saveAndFlush(motivosCancelacion);

        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();

        // Update the motivosCancelacion using partial update
        MotivosCancelacion partialUpdatedMotivosCancelacion = new MotivosCancelacion();
        partialUpdatedMotivosCancelacion.setId(motivosCancelacion.getId());

        partialUpdatedMotivosCancelacion.fecha(UPDATED_FECHA);

        restMotivosCancelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotivosCancelacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMotivosCancelacion))
            )
            .andExpect(status().isOk());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
        MotivosCancelacion testMotivosCancelacion = motivosCancelacionList.get(motivosCancelacionList.size() - 1);
        assertThat(testMotivosCancelacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMotivosCancelacion.getMotivoCancelacion()).isEqualTo(DEFAULT_MOTIVO_CANCELACION);
        assertThat(testMotivosCancelacion.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testMotivosCancelacion.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testMotivosCancelacion.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateMotivosCancelacionWithPatch() throws Exception {
        // Initialize the database
        motivosCancelacionRepository.saveAndFlush(motivosCancelacion);

        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();

        // Update the motivosCancelacion using partial update
        MotivosCancelacion partialUpdatedMotivosCancelacion = new MotivosCancelacion();
        partialUpdatedMotivosCancelacion.setId(motivosCancelacion.getId());

        partialUpdatedMotivosCancelacion
            .fecha(UPDATED_FECHA)
            .motivoCancelacion(UPDATED_MOTIVO_CANCELACION)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restMotivosCancelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMotivosCancelacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMotivosCancelacion))
            )
            .andExpect(status().isOk());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
        MotivosCancelacion testMotivosCancelacion = motivosCancelacionList.get(motivosCancelacionList.size() - 1);
        assertThat(testMotivosCancelacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testMotivosCancelacion.getMotivoCancelacion()).isEqualTo(UPDATED_MOTIVO_CANCELACION);
        assertThat(testMotivosCancelacion.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testMotivosCancelacion.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testMotivosCancelacion.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingMotivosCancelacion() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();
        motivosCancelacion.setId(count.incrementAndGet());

        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMotivosCancelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, motivosCancelacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMotivosCancelacion() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();
        motivosCancelacion.setId(count.incrementAndGet());

        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivosCancelacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMotivosCancelacion() throws Exception {
        int databaseSizeBeforeUpdate = motivosCancelacionRepository.findAll().size();
        motivosCancelacion.setId(count.incrementAndGet());

        // Create the MotivosCancelacion
        MotivosCancelacionDTO motivosCancelacionDTO = motivosCancelacionMapper.toDto(motivosCancelacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMotivosCancelacionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(motivosCancelacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MotivosCancelacion in the database
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMotivosCancelacion() throws Exception {
        // Initialize the database
        motivosCancelacionRepository.saveAndFlush(motivosCancelacion);

        int databaseSizeBeforeDelete = motivosCancelacionRepository.findAll().size();

        // Delete the motivosCancelacion
        restMotivosCancelacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, motivosCancelacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MotivosCancelacion> motivosCancelacionList = motivosCancelacionRepository.findAll();
        assertThat(motivosCancelacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
