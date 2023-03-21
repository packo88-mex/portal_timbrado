package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TipoReciboRepository;
import com.mycompany.myapp.service.TipoReciboService;
import com.mycompany.myapp.service.dto.TipoReciboDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoRecibo}.
 */
@RestController
@RequestMapping("/api")
public class TipoReciboResource {

    private final Logger log = LoggerFactory.getLogger(TipoReciboResource.class);

    private static final String ENTITY_NAME = "tipoRecibo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoReciboService tipoReciboService;

    private final TipoReciboRepository tipoReciboRepository;

    public TipoReciboResource(TipoReciboService tipoReciboService, TipoReciboRepository tipoReciboRepository) {
        this.tipoReciboService = tipoReciboService;
        this.tipoReciboRepository = tipoReciboRepository;
    }

    /**
     * {@code POST  /tipo-recibos} : Create a new tipoRecibo.
     *
     * @param tipoReciboDTO the tipoReciboDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoReciboDTO, or with status {@code 400 (Bad Request)} if the tipoRecibo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-recibos")
    public ResponseEntity<TipoReciboDTO> createTipoRecibo(@Valid @RequestBody TipoReciboDTO tipoReciboDTO) throws URISyntaxException {
        log.debug("REST request to save TipoRecibo : {}", tipoReciboDTO);
        if (tipoReciboDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoRecibo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoReciboDTO result = tipoReciboService.save(tipoReciboDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-recibos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-recibos/:id} : Updates an existing tipoRecibo.
     *
     * @param id the id of the tipoReciboDTO to save.
     * @param tipoReciboDTO the tipoReciboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoReciboDTO,
     * or with status {@code 400 (Bad Request)} if the tipoReciboDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoReciboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-recibos/{id}")
    public ResponseEntity<TipoReciboDTO> updateTipoRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoReciboDTO tipoReciboDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoRecibo : {}, {}", id, tipoReciboDTO);
        if (tipoReciboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoReciboDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoReciboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoReciboDTO result = tipoReciboService.update(tipoReciboDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoReciboDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-recibos/:id} : Partial updates given fields of an existing tipoRecibo, field will ignore if it is null
     *
     * @param id the id of the tipoReciboDTO to save.
     * @param tipoReciboDTO the tipoReciboDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoReciboDTO,
     * or with status {@code 400 (Bad Request)} if the tipoReciboDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoReciboDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoReciboDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-recibos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoReciboDTO> partialUpdateTipoRecibo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoReciboDTO tipoReciboDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoRecibo partially : {}, {}", id, tipoReciboDTO);
        if (tipoReciboDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoReciboDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoReciboRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoReciboDTO> result = tipoReciboService.partialUpdate(tipoReciboDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoReciboDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-recibos} : get all the tipoRecibos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoRecibos in body.
     */
    @GetMapping("/tipo-recibos")
    public List<TipoReciboDTO> getAllTipoRecibos() {
        log.debug("REST request to get all TipoRecibos");
        return tipoReciboService.findAll();
    }

    /**
     * {@code GET  /tipo-recibos/:id} : get the "id" tipoRecibo.
     *
     * @param id the id of the tipoReciboDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoReciboDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-recibos/{id}")
    public ResponseEntity<TipoReciboDTO> getTipoRecibo(@PathVariable Long id) {
        log.debug("REST request to get TipoRecibo : {}", id);
        Optional<TipoReciboDTO> tipoReciboDTO = tipoReciboService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoReciboDTO);
    }

    /**
     * {@code DELETE  /tipo-recibos/:id} : delete the "id" tipoRecibo.
     *
     * @param id the id of the tipoReciboDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-recibos/{id}")
    public ResponseEntity<Void> deleteTipoRecibo(@PathVariable Long id) {
        log.debug("REST request to delete TipoRecibo : {}", id);
        tipoReciboService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
