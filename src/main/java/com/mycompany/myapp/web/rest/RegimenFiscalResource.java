package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RegimenFiscalRepository;
import com.mycompany.myapp.service.RegimenFiscalService;
import com.mycompany.myapp.service.dto.RegimenFiscalDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.RegimenFiscal}.
 */
@RestController
@RequestMapping("/api")
public class RegimenFiscalResource {

    private final Logger log = LoggerFactory.getLogger(RegimenFiscalResource.class);

    private static final String ENTITY_NAME = "regimenFiscal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RegimenFiscalService regimenFiscalService;

    private final RegimenFiscalRepository regimenFiscalRepository;

    public RegimenFiscalResource(RegimenFiscalService regimenFiscalService, RegimenFiscalRepository regimenFiscalRepository) {
        this.regimenFiscalService = regimenFiscalService;
        this.regimenFiscalRepository = regimenFiscalRepository;
    }

    /**
     * {@code POST  /regimen-fiscals} : Create a new regimenFiscal.
     *
     * @param regimenFiscalDTO the regimenFiscalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new regimenFiscalDTO, or with status {@code 400 (Bad Request)} if the regimenFiscal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/regimen-fiscals")
    public ResponseEntity<RegimenFiscalDTO> createRegimenFiscal(@Valid @RequestBody RegimenFiscalDTO regimenFiscalDTO)
        throws URISyntaxException {
        log.debug("REST request to save RegimenFiscal : {}", regimenFiscalDTO);
        if (regimenFiscalDTO.getId() != null) {
            throw new BadRequestAlertException("A new regimenFiscal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RegimenFiscalDTO result = regimenFiscalService.save(regimenFiscalDTO);
        return ResponseEntity
            .created(new URI("/api/regimen-fiscals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /regimen-fiscals/:id} : Updates an existing regimenFiscal.
     *
     * @param id the id of the regimenFiscalDTO to save.
     * @param regimenFiscalDTO the regimenFiscalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regimenFiscalDTO,
     * or with status {@code 400 (Bad Request)} if the regimenFiscalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the regimenFiscalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/regimen-fiscals/{id}")
    public ResponseEntity<RegimenFiscalDTO> updateRegimenFiscal(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RegimenFiscalDTO regimenFiscalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RegimenFiscal : {}, {}", id, regimenFiscalDTO);
        if (regimenFiscalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regimenFiscalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regimenFiscalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RegimenFiscalDTO result = regimenFiscalService.update(regimenFiscalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regimenFiscalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /regimen-fiscals/:id} : Partial updates given fields of an existing regimenFiscal, field will ignore if it is null
     *
     * @param id the id of the regimenFiscalDTO to save.
     * @param regimenFiscalDTO the regimenFiscalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated regimenFiscalDTO,
     * or with status {@code 400 (Bad Request)} if the regimenFiscalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the regimenFiscalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the regimenFiscalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/regimen-fiscals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RegimenFiscalDTO> partialUpdateRegimenFiscal(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RegimenFiscalDTO regimenFiscalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RegimenFiscal partially : {}, {}", id, regimenFiscalDTO);
        if (regimenFiscalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, regimenFiscalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!regimenFiscalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RegimenFiscalDTO> result = regimenFiscalService.partialUpdate(regimenFiscalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, regimenFiscalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /regimen-fiscals} : get all the regimenFiscals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of regimenFiscals in body.
     */
    @GetMapping("/regimen-fiscals")
    public List<RegimenFiscalDTO> getAllRegimenFiscals() {
        log.debug("REST request to get all RegimenFiscals");
        return regimenFiscalService.findAll();
    }

    /**
     * {@code GET  /regimen-fiscals/:id} : get the "id" regimenFiscal.
     *
     * @param id the id of the regimenFiscalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the regimenFiscalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/regimen-fiscals/{id}")
    public ResponseEntity<RegimenFiscalDTO> getRegimenFiscal(@PathVariable Long id) {
        log.debug("REST request to get RegimenFiscal : {}", id);
        Optional<RegimenFiscalDTO> regimenFiscalDTO = regimenFiscalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regimenFiscalDTO);
    }

    /**
     * {@code DELETE  /regimen-fiscals/:id} : delete the "id" regimenFiscal.
     *
     * @param id the id of the regimenFiscalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/regimen-fiscals/{id}")
    public ResponseEntity<Void> deleteRegimenFiscal(@PathVariable Long id) {
        log.debug("REST request to delete RegimenFiscal : {}", id);
        regimenFiscalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
