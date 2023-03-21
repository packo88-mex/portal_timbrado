package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RepresentacionGraficaRepository;
import com.mycompany.myapp.service.RepresentacionGraficaService;
import com.mycompany.myapp.service.dto.RepresentacionGraficaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RepresentacionGrafica}.
 */
@RestController
@RequestMapping("/api")
public class RepresentacionGraficaResource {

    private final Logger log = LoggerFactory.getLogger(RepresentacionGraficaResource.class);

    private static final String ENTITY_NAME = "representacionGrafica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RepresentacionGraficaService representacionGraficaService;

    private final RepresentacionGraficaRepository representacionGraficaRepository;

    public RepresentacionGraficaResource(
        RepresentacionGraficaService representacionGraficaService,
        RepresentacionGraficaRepository representacionGraficaRepository
    ) {
        this.representacionGraficaService = representacionGraficaService;
        this.representacionGraficaRepository = representacionGraficaRepository;
    }

    /**
     * {@code POST  /representacion-graficas} : Create a new representacionGrafica.
     *
     * @param representacionGraficaDTO the representacionGraficaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new representacionGraficaDTO, or with status {@code 400 (Bad Request)} if the representacionGrafica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/representacion-graficas")
    public ResponseEntity<RepresentacionGraficaDTO> createRepresentacionGrafica(
        @Valid @RequestBody RepresentacionGraficaDTO representacionGraficaDTO
    ) throws URISyntaxException {
        log.debug("REST request to save RepresentacionGrafica : {}", representacionGraficaDTO);
        if (representacionGraficaDTO.getId() != null) {
            throw new BadRequestAlertException("A new representacionGrafica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RepresentacionGraficaDTO result = representacionGraficaService.save(representacionGraficaDTO);
        return ResponseEntity
            .created(new URI("/api/representacion-graficas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /representacion-graficas/:id} : Updates an existing representacionGrafica.
     *
     * @param id the id of the representacionGraficaDTO to save.
     * @param representacionGraficaDTO the representacionGraficaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representacionGraficaDTO,
     * or with status {@code 400 (Bad Request)} if the representacionGraficaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the representacionGraficaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/representacion-graficas/{id}")
    public ResponseEntity<RepresentacionGraficaDTO> updateRepresentacionGrafica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RepresentacionGraficaDTO representacionGraficaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RepresentacionGrafica : {}, {}", id, representacionGraficaDTO);
        if (representacionGraficaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representacionGraficaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representacionGraficaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RepresentacionGraficaDTO result = representacionGraficaService.update(representacionGraficaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representacionGraficaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /representacion-graficas/:id} : Partial updates given fields of an existing representacionGrafica, field will ignore if it is null
     *
     * @param id the id of the representacionGraficaDTO to save.
     * @param representacionGraficaDTO the representacionGraficaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated representacionGraficaDTO,
     * or with status {@code 400 (Bad Request)} if the representacionGraficaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the representacionGraficaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the representacionGraficaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/representacion-graficas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RepresentacionGraficaDTO> partialUpdateRepresentacionGrafica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RepresentacionGraficaDTO representacionGraficaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RepresentacionGrafica partially : {}, {}", id, representacionGraficaDTO);
        if (representacionGraficaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, representacionGraficaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!representacionGraficaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RepresentacionGraficaDTO> result = representacionGraficaService.partialUpdate(representacionGraficaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, representacionGraficaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /representacion-graficas} : get all the representacionGraficas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of representacionGraficas in body.
     */
    @GetMapping("/representacion-graficas")
    public List<RepresentacionGraficaDTO> getAllRepresentacionGraficas() {
        log.debug("REST request to get all RepresentacionGraficas");
        return representacionGraficaService.findAll();
    }

    /**
     * {@code GET  /representacion-graficas/:id} : get the "id" representacionGrafica.
     *
     * @param id the id of the representacionGraficaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the representacionGraficaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/representacion-graficas/{id}")
    public ResponseEntity<RepresentacionGraficaDTO> getRepresentacionGrafica(@PathVariable Long id) {
        log.debug("REST request to get RepresentacionGrafica : {}", id);
        Optional<RepresentacionGraficaDTO> representacionGraficaDTO = representacionGraficaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(representacionGraficaDTO);
    }

    /**
     * {@code DELETE  /representacion-graficas/:id} : delete the "id" representacionGrafica.
     *
     * @param id the id of the representacionGraficaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/representacion-graficas/{id}")
    public ResponseEntity<Void> deleteRepresentacionGrafica(@PathVariable Long id) {
        log.debug("REST request to delete RepresentacionGrafica : {}", id);
        representacionGraficaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
