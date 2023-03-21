package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TipoCfdiRepository;
import com.mycompany.myapp.service.TipoCfdiService;
import com.mycompany.myapp.service.dto.TipoCfdiDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TipoCfdi}.
 */
@RestController
@RequestMapping("/api")
public class TipoCfdiResource {

    private final Logger log = LoggerFactory.getLogger(TipoCfdiResource.class);

    private static final String ENTITY_NAME = "tipoCfdi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoCfdiService tipoCfdiService;

    private final TipoCfdiRepository tipoCfdiRepository;

    public TipoCfdiResource(TipoCfdiService tipoCfdiService, TipoCfdiRepository tipoCfdiRepository) {
        this.tipoCfdiService = tipoCfdiService;
        this.tipoCfdiRepository = tipoCfdiRepository;
    }

    /**
     * {@code POST  /tipo-cfdis} : Create a new tipoCfdi.
     *
     * @param tipoCfdiDTO the tipoCfdiDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoCfdiDTO, or with status {@code 400 (Bad Request)} if the tipoCfdi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-cfdis")
    public ResponseEntity<TipoCfdiDTO> createTipoCfdi(@Valid @RequestBody TipoCfdiDTO tipoCfdiDTO) throws URISyntaxException {
        log.debug("REST request to save TipoCfdi : {}", tipoCfdiDTO);
        if (tipoCfdiDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoCfdi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoCfdiDTO result = tipoCfdiService.save(tipoCfdiDTO);
        return ResponseEntity
            .created(new URI("/api/tipo-cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-cfdis/:id} : Updates an existing tipoCfdi.
     *
     * @param id the id of the tipoCfdiDTO to save.
     * @param tipoCfdiDTO the tipoCfdiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoCfdiDTO,
     * or with status {@code 400 (Bad Request)} if the tipoCfdiDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoCfdiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-cfdis/{id}")
    public ResponseEntity<TipoCfdiDTO> updateTipoCfdi(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TipoCfdiDTO tipoCfdiDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TipoCfdi : {}, {}", id, tipoCfdiDTO);
        if (tipoCfdiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoCfdiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoCfdiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TipoCfdiDTO result = tipoCfdiService.update(tipoCfdiDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoCfdiDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tipo-cfdis/:id} : Partial updates given fields of an existing tipoCfdi, field will ignore if it is null
     *
     * @param id the id of the tipoCfdiDTO to save.
     * @param tipoCfdiDTO the tipoCfdiDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoCfdiDTO,
     * or with status {@code 400 (Bad Request)} if the tipoCfdiDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tipoCfdiDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tipoCfdiDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tipo-cfdis/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TipoCfdiDTO> partialUpdateTipoCfdi(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TipoCfdiDTO tipoCfdiDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TipoCfdi partially : {}, {}", id, tipoCfdiDTO);
        if (tipoCfdiDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tipoCfdiDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tipoCfdiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TipoCfdiDTO> result = tipoCfdiService.partialUpdate(tipoCfdiDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoCfdiDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tipo-cfdis} : get all the tipoCfdis.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoCfdis in body.
     */
    @GetMapping("/tipo-cfdis")
    public List<TipoCfdiDTO> getAllTipoCfdis() {
        log.debug("REST request to get all TipoCfdis");
        return tipoCfdiService.findAll();
    }

    /**
     * {@code GET  /tipo-cfdis/:id} : get the "id" tipoCfdi.
     *
     * @param id the id of the tipoCfdiDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoCfdiDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-cfdis/{id}")
    public ResponseEntity<TipoCfdiDTO> getTipoCfdi(@PathVariable Long id) {
        log.debug("REST request to get TipoCfdi : {}", id);
        Optional<TipoCfdiDTO> tipoCfdiDTO = tipoCfdiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoCfdiDTO);
    }

    /**
     * {@code DELETE  /tipo-cfdis/:id} : delete the "id" tipoCfdi.
     *
     * @param id the id of the tipoCfdiDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-cfdis/{id}")
    public ResponseEntity<Void> deleteTipoCfdi(@PathVariable Long id) {
        log.debug("REST request to delete TipoCfdi : {}", id);
        tipoCfdiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
