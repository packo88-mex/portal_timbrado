package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TipoReciboDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TipoRecibo}.
 */
public interface TipoReciboService {
    /**
     * Save a tipoRecibo.
     *
     * @param tipoReciboDTO the entity to save.
     * @return the persisted entity.
     */
    TipoReciboDTO save(TipoReciboDTO tipoReciboDTO);

    /**
     * Updates a tipoRecibo.
     *
     * @param tipoReciboDTO the entity to update.
     * @return the persisted entity.
     */
    TipoReciboDTO update(TipoReciboDTO tipoReciboDTO);

    /**
     * Partially updates a tipoRecibo.
     *
     * @param tipoReciboDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoReciboDTO> partialUpdate(TipoReciboDTO tipoReciboDTO);

    /**
     * Get all the tipoRecibos.
     *
     * @return the list of entities.
     */
    List<TipoReciboDTO> findAll();

    /**
     * Get the "id" tipoRecibo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoReciboDTO> findOne(Long id);

    /**
     * Delete the "id" tipoRecibo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
