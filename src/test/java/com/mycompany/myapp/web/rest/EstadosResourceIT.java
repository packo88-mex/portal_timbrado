package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Estados;
import com.mycompany.myapp.domain.enumeration.Estatus;
import com.mycompany.myapp.repository.EstadosRepository;
import com.mycompany.myapp.service.dto.EstadosDTO;
import com.mycompany.myapp.service.mapper.EstadosMapper;
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
 * Integration tests for the {@link EstadosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadosResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_PAIS = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_ESTADO = "BBBBBBBBBB";

    private static final Estatus DEFAULT_ESTATUS = Estatus.Activo;
    private static final Estatus UPDATED_ESTATUS = Estatus.Inactivo;

    private static final LocalDate DEFAULT_FECHA_MODIFICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_MODIFICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/estados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstadosRepository estadosRepository;

    @Autowired
    private EstadosMapper estadosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadosMockMvc;

    private Estados estados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estados createEntity(EntityManager em) {
        Estados estados = new Estados()
            .fecha(DEFAULT_FECHA)
            .pais(DEFAULT_PAIS)
            .estado(DEFAULT_ESTADO)
            .descripcionEstado(DEFAULT_DESCRIPCION_ESTADO)
            .estatus(DEFAULT_ESTATUS)
            .fechaModificacion(DEFAULT_FECHA_MODIFICACION)
            .usuario(DEFAULT_USUARIO);
        return estados;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Estados createUpdatedEntity(EntityManager em) {
        Estados estados = new Estados()
            .fecha(UPDATED_FECHA)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .descripcionEstado(UPDATED_DESCRIPCION_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        return estados;
    }

    @BeforeEach
    public void initTest() {
        estados = createEntity(em);
    }

    @Test
    @Transactional
    void createEstados() throws Exception {
        int databaseSizeBeforeCreate = estadosRepository.findAll().size();
        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);
        restEstadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isCreated());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeCreate + 1);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstados.getPais()).isEqualTo(DEFAULT_PAIS);
        assertThat(testEstados.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEstados.getDescripcionEstado()).isEqualTo(DEFAULT_DESCRIPCION_ESTADO);
        assertThat(testEstados.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstados.getFechaModificacion()).isEqualTo(DEFAULT_FECHA_MODIFICACION);
        assertThat(testEstados.getUsuario()).isEqualTo(DEFAULT_USUARIO);
    }

    @Test
    @Transactional
    void createEstadosWithExistingId() throws Exception {
        // Create the Estados with an existing ID
        estados.setId(1L);
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        int databaseSizeBeforeCreate = estadosRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        // Get all the estadosList
        restEstadosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estados.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].pais").value(hasItem(DEFAULT_PAIS)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)))
            .andExpect(jsonPath("$.[*].descripcionEstado").value(hasItem(DEFAULT_DESCRIPCION_ESTADO)))
            .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
            .andExpect(jsonPath("$.[*].fechaModificacion").value(hasItem(DEFAULT_FECHA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)));
    }

    @Test
    @Transactional
    void getEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        // Get the estados
        restEstadosMockMvc
            .perform(get(ENTITY_API_URL_ID, estados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estados.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.pais").value(DEFAULT_PAIS))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO))
            .andExpect(jsonPath("$.descripcionEstado").value(DEFAULT_DESCRIPCION_ESTADO))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.fechaModificacion").value(DEFAULT_FECHA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO));
    }

    @Test
    @Transactional
    void getNonExistingEstados() throws Exception {
        // Get the estados
        restEstadosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Update the estados
        Estados updatedEstados = estadosRepository.findById(estados.getId()).get();
        // Disconnect from session so that the updates on updatedEstados are not directly saved in db
        em.detach(updatedEstados);
        updatedEstados
            .fecha(UPDATED_FECHA)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .descripcionEstado(UPDATED_DESCRIPCION_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);
        EstadosDTO estadosDTO = estadosMapper.toDto(updatedEstados);

        restEstadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstados.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEstados.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEstados.getDescripcionEstado()).isEqualTo(UPDATED_DESCRIPCION_ESTADO);
        assertThat(testEstados.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstados.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testEstados.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void putNonExistingEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();
        estados.setId(count.incrementAndGet());

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();
        estados.setId(count.incrementAndGet());

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();
        estados.setId(count.incrementAndGet());

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estadosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadosWithPatch() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Update the estados using partial update
        Estados partialUpdatedEstados = new Estados();
        partialUpdatedEstados.setId(estados.getId());

        partialUpdatedEstados
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restEstadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstados.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstados))
            )
            .andExpect(status().isOk());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testEstados.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEstados.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEstados.getDescripcionEstado()).isEqualTo(DEFAULT_DESCRIPCION_ESTADO);
        assertThat(testEstados.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testEstados.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testEstados.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void fullUpdateEstadosWithPatch() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();

        // Update the estados using partial update
        Estados partialUpdatedEstados = new Estados();
        partialUpdatedEstados.setId(estados.getId());

        partialUpdatedEstados
            .fecha(UPDATED_FECHA)
            .pais(UPDATED_PAIS)
            .estado(UPDATED_ESTADO)
            .descripcionEstado(UPDATED_DESCRIPCION_ESTADO)
            .estatus(UPDATED_ESTATUS)
            .fechaModificacion(UPDATED_FECHA_MODIFICACION)
            .usuario(UPDATED_USUARIO);

        restEstadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstados.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstados))
            )
            .andExpect(status().isOk());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
        Estados testEstados = estadosList.get(estadosList.size() - 1);
        assertThat(testEstados.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testEstados.getPais()).isEqualTo(UPDATED_PAIS);
        assertThat(testEstados.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEstados.getDescripcionEstado()).isEqualTo(UPDATED_DESCRIPCION_ESTADO);
        assertThat(testEstados.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testEstados.getFechaModificacion()).isEqualTo(UPDATED_FECHA_MODIFICACION);
        assertThat(testEstados.getUsuario()).isEqualTo(UPDATED_USUARIO);
    }

    @Test
    @Transactional
    void patchNonExistingEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();
        estados.setId(count.incrementAndGet());

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();
        estados.setId(count.incrementAndGet());

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstados() throws Exception {
        int databaseSizeBeforeUpdate = estadosRepository.findAll().size();
        estados.setId(count.incrementAndGet());

        // Create the Estados
        EstadosDTO estadosDTO = estadosMapper.toDto(estados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadosMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(estadosDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Estados in the database
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstados() throws Exception {
        // Initialize the database
        estadosRepository.saveAndFlush(estados);

        int databaseSizeBeforeDelete = estadosRepository.findAll().size();

        // Delete the estados
        restEstadosMockMvc
            .perform(delete(ENTITY_API_URL_ID, estados.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Estados> estadosList = estadosRepository.findAll();
        assertThat(estadosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
