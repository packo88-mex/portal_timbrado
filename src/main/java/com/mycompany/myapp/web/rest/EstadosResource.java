package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EstadosRepository;
import com.mycompany.myapp.service.EstadosService;
import com.mycompany.myapp.service.dto.EstadosDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Estados}.
 */
@RestController
@RequestMapping("/api")
public class EstadosResource {

    private final Logger log = LoggerFactory.getLogger(EstadosResource.class);

    private static final String ENTITY_NAME = "estados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstadosService estadosService;

    private final EstadosRepository estadosRepository;

    public EstadosResource(EstadosService estadosService, EstadosRepository estadosRepository) {
        this.estadosService = estadosService;
        this.estadosRepository = estadosRepository;
    }

    /**
     * {@code POST  /estados} : Create a new estados.
     *
     * @param estadosDTO the estadosDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estadosDTO, or with status {@code 400 (Bad Request)} if the estados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estados")
    public ResponseEntity<EstadosDTO> createEstados(@Valid @RequestBody EstadosDTO estadosDTO) throws URISyntaxException {
        log.debug("REST request to save Estados : {}", estadosDTO);
        if (estadosDTO.getId() != null) {
            throw new BadRequestAlertException("A new estados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstadosDTO result = estadosService.save(estadosDTO);
        return ResponseEntity
            .created(new URI("/api/estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estados/:id} : Updates an existing estados.
     *
     * @param id the id of the estadosDTO to save.
     * @param estadosDTO the estadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadosDTO,
     * or with status {@code 400 (Bad Request)} if the estadosDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estados/{id}")
    public ResponseEntity<EstadosDTO> updateEstados(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstadosDTO estadosDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Estados : {}, {}", id, estadosDTO);
        if (estadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstadosDTO result = estadosService.update(estadosDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadosDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estados/:id} : Partial updates given fields of an existing estados, field will ignore if it is null
     *
     * @param id the id of the estadosDTO to save.
     * @param estadosDTO the estadosDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estadosDTO,
     * or with status {@code 400 (Bad Request)} if the estadosDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estadosDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estadosDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estados/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstadosDTO> partialUpdateEstados(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstadosDTO estadosDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Estados partially : {}, {}", id, estadosDTO);
        if (estadosDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estadosDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estadosRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstadosDTO> result = estadosService.partialUpdate(estadosDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estadosDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estados} : get all the estados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estados in body.
     */
    @GetMapping("/estados")
    public List<EstadosDTO> getAllEstados() {
        log.debug("REST request to get all Estados");
        return estadosService.findAll();
    }

    /**
     * {@code GET  /estados/:id} : get the "id" estados.
     *
     * @param id the id of the estadosDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estadosDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estados/{id}")
    public ResponseEntity<EstadosDTO> getEstados(@PathVariable Long id) {
        log.debug("REST request to get Estados : {}", id);
        Optional<EstadosDTO> estadosDTO = estadosService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estadosDTO);
    }

    /**
     * {@code DELETE  /estados/:id} : delete the "id" estados.
     *
     * @param id the id of the estadosDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estados/{id}")
    public ResponseEntity<Void> deleteEstados(@PathVariable Long id) {
        log.debug("REST request to delete Estados : {}", id);
        estadosService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
