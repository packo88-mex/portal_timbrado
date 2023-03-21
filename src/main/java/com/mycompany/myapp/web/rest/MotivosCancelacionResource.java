package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MotivosCancelacionRepository;
import com.mycompany.myapp.service.MotivosCancelacionService;
import com.mycompany.myapp.service.dto.MotivosCancelacionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.MotivosCancelacion}.
 */
@RestController
@RequestMapping("/api")
public class MotivosCancelacionResource {

    private final Logger log = LoggerFactory.getLogger(MotivosCancelacionResource.class);

    private static final String ENTITY_NAME = "motivosCancelacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MotivosCancelacionService motivosCancelacionService;

    private final MotivosCancelacionRepository motivosCancelacionRepository;

    public MotivosCancelacionResource(
        MotivosCancelacionService motivosCancelacionService,
        MotivosCancelacionRepository motivosCancelacionRepository
    ) {
        this.motivosCancelacionService = motivosCancelacionService;
        this.motivosCancelacionRepository = motivosCancelacionRepository;
    }

    /**
     * {@code POST  /motivos-cancelacions} : Create a new motivosCancelacion.
     *
     * @param motivosCancelacionDTO the motivosCancelacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new motivosCancelacionDTO, or with status {@code 400 (Bad Request)} if the motivosCancelacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/motivos-cancelacions")
    public ResponseEntity<MotivosCancelacionDTO> createMotivosCancelacion(@Valid @RequestBody MotivosCancelacionDTO motivosCancelacionDTO)
        throws URISyntaxException {
        log.debug("REST request to save MotivosCancelacion : {}", motivosCancelacionDTO);
        if (motivosCancelacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new motivosCancelacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MotivosCancelacionDTO result = motivosCancelacionService.save(motivosCancelacionDTO);
        return ResponseEntity
            .created(new URI("/api/motivos-cancelacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /motivos-cancelacions/:id} : Updates an existing motivosCancelacion.
     *
     * @param id the id of the motivosCancelacionDTO to save.
     * @param motivosCancelacionDTO the motivosCancelacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivosCancelacionDTO,
     * or with status {@code 400 (Bad Request)} if the motivosCancelacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the motivosCancelacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/motivos-cancelacions/{id}")
    public ResponseEntity<MotivosCancelacionDTO> updateMotivosCancelacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MotivosCancelacionDTO motivosCancelacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MotivosCancelacion : {}, {}", id, motivosCancelacionDTO);
        if (motivosCancelacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motivosCancelacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!motivosCancelacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MotivosCancelacionDTO result = motivosCancelacionService.update(motivosCancelacionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, motivosCancelacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /motivos-cancelacions/:id} : Partial updates given fields of an existing motivosCancelacion, field will ignore if it is null
     *
     * @param id the id of the motivosCancelacionDTO to save.
     * @param motivosCancelacionDTO the motivosCancelacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated motivosCancelacionDTO,
     * or with status {@code 400 (Bad Request)} if the motivosCancelacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the motivosCancelacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the motivosCancelacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/motivos-cancelacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MotivosCancelacionDTO> partialUpdateMotivosCancelacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MotivosCancelacionDTO motivosCancelacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MotivosCancelacion partially : {}, {}", id, motivosCancelacionDTO);
        if (motivosCancelacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, motivosCancelacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!motivosCancelacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MotivosCancelacionDTO> result = motivosCancelacionService.partialUpdate(motivosCancelacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, motivosCancelacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /motivos-cancelacions} : get all the motivosCancelacions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of motivosCancelacions in body.
     */
    @GetMapping("/motivos-cancelacions")
    public List<MotivosCancelacionDTO> getAllMotivosCancelacions() {
        log.debug("REST request to get all MotivosCancelacions");
        return motivosCancelacionService.findAll();
    }

    /**
     * {@code GET  /motivos-cancelacions/:id} : get the "id" motivosCancelacion.
     *
     * @param id the id of the motivosCancelacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the motivosCancelacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/motivos-cancelacions/{id}")
    public ResponseEntity<MotivosCancelacionDTO> getMotivosCancelacion(@PathVariable Long id) {
        log.debug("REST request to get MotivosCancelacion : {}", id);
        Optional<MotivosCancelacionDTO> motivosCancelacionDTO = motivosCancelacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(motivosCancelacionDTO);
    }

    /**
     * {@code DELETE  /motivos-cancelacions/:id} : delete the "id" motivosCancelacion.
     *
     * @param id the id of the motivosCancelacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/motivos-cancelacions/{id}")
    public ResponseEntity<Void> deleteMotivosCancelacion(@PathVariable Long id) {
        log.debug("REST request to delete MotivosCancelacion : {}", id);
        motivosCancelacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
