package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EstadosDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Estados}.
 */
public interface EstadosService {
    /**
     * Save a estados.
     *
     * @param estadosDTO the entity to save.
     * @return the persisted entity.
     */
    EstadosDTO save(EstadosDTO estadosDTO);

    /**
     * Updates a estados.
     *
     * @param estadosDTO the entity to update.
     * @return the persisted entity.
     */
    EstadosDTO update(EstadosDTO estadosDTO);

    /**
     * Partially updates a estados.
     *
     * @param estadosDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstadosDTO> partialUpdate(EstadosDTO estadosDTO);

    /**
     * Get all the estados.
     *
     * @return the list of entities.
     */
    List<EstadosDTO> findAll();

    /**
     * Get the "id" estados.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstadosDTO> findOne(Long id);

    /**
     * Delete the "id" estados.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
