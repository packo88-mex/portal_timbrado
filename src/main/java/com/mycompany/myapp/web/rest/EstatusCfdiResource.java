package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EstatusCfdiRepository;
import com.mycompany.myapp.service.EstatusCfdiService;
import com.mycompany.myapp.service.dto.EstatusCfdiDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.EstatusCfdi}.
 */
@RestController
@RequestMapping("/api")
public class EstatusCfdiResource {

    private final Logger log = LoggerFactory.getLogger(EstatusCfdiResource.class);

    private static final String ENTITY_NAME = "estatusCfdi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstatusCfdiService estatusCfdiService;

    private final EstatusCfdiRepository estatusCfdiRepository;

    public EstatusCfdiResource(EstatusCfdiService estatusCfdiService, EstatusCfdiRepository estatusCfdiRepository) {
        this.estatusCfdiService = estatusCfdiService;
        this.estatusCfdiRepository = estatusCfdiRepository;
    }

    /**
     * {@code POST  /estatus-cfdis} : Create a new estatusCfdi.
     *
     * @param estatusCfdiDTO the estatusCfdiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estatusCfdiDTO, or with status {@code 400 (Bad Request)} if the estatusCfdi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estatus-cfdis")
    public ResponseEntity<EstatusCfdiDTO> createEstatusCfdi(@Valid @RequestBody EstatusCfdiDTO estatusCfdiDTO) throws URISyntaxException {
        log.debug("REST request to save EstatusCfdi : {}", estatusCfdiDTO);
        if (estatusCfdiDTO.getId() != null) {
            throw new BadRequestAlertException("A new estatusCfdi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstatusCfdiDTO result = estatusCfdiService.save(estatusCfdiDTO);
        return ResponseEntity
            .created(new URI("/api/estatus-cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estatus-cfdis/:id} : Updates an existing estatusCfdi.
     *
     * @param id the id of the estatusCfdiDTO to save.
     * @param estatusCfdiDTO the estatusCfdiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusCfdiDTO,
     * or with status {@code 400 (Bad Request)} if the estatusCfdiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estatusCfdiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estatus-cfdis/{id}")
    public ResponseEntity<EstatusCfdiDTO> updateEstatusCfdi(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstatusCfdiDTO estatusCfdiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstatusCfdi : {}, {}", id, estatusCfdiDTO);
        if (estatusCfdiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estatusCfdiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estatusCfdiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstatusCfdiDTO result = estatusCfdiService.update(estatusCfdiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusCfdiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estatus-cfdis/:id} : Partial updates given fields of an existing estatusCfdi, field will ignore if it is null
     *
     * @param id the id of the estatusCfdiDTO to save.
     * @param estatusCfdiDTO the estatusCfdiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estatusCfdiDTO,
     * or with status {@code 400 (Bad Request)} if the estatusCfdiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estatusCfdiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estatusCfdiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estatus-cfdis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstatusCfdiDTO> partialUpdateEstatusCfdi(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstatusCfdiDTO estatusCfdiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstatusCfdi partially : {}, {}", id, estatusCfdiDTO);
        if (estatusCfdiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estatusCfdiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estatusCfdiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstatusCfdiDTO> result = estatusCfdiService.partialUpdate(estatusCfdiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estatusCfdiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estatus-cfdis} : get all the estatusCfdis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estatusCfdis in body.
     */
    @GetMapping("/estatus-cfdis")
    public List<EstatusCfdiDTO> getAllEstatusCfdis() {
        log.debug("REST request to get all EstatusCfdis");
        return estatusCfdiService.findAll();
    }

    /**
     * {@code GET  /estatus-cfdis/:id} : get the "id" estatusCfdi.
     *
     * @param id the id of the estatusCfdiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estatusCfdiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estatus-cfdis/{id}")
    public ResponseEntity<EstatusCfdiDTO> getEstatusCfdi(@PathVariable Long id) {
        log.debug("REST request to get EstatusCfdi : {}", id);
        Optional<EstatusCfdiDTO> estatusCfdiDTO = estatusCfdiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estatusCfdiDTO);
    }

    /**
     * {@code DELETE  /estatus-cfdis/:id} : delete the "id" estatusCfdi.
     *
     * @param id the id of the estatusCfdiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estatus-cfdis/{id}")
    public ResponseEntity<Void> deleteEstatusCfdi(@PathVariable Long id) {
        log.debug("REST request to delete EstatusCfdi : {}", id);
        estatusCfdiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
