package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.TipoCfdiDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.TipoCfdi}.
 */
public interface TipoCfdiService {
    /**
     * Save a tipoCfdi.
     *
     * @param tipoCfdiDTO the entity to save.
     * @return the persisted entity.
     */
    TipoCfdiDTO save(TipoCfdiDTO tipoCfdiDTO);

    /**
     * Updates a tipoCfdi.
     *
     * @param tipoCfdiDTO the entity to update.
     * @return the persisted entity.
     */
    TipoCfdiDTO update(TipoCfdiDTO tipoCfdiDTO);

    /**
     * Partially updates a tipoCfdi.
     *
     * @param tipoCfdiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TipoCfdiDTO> partialUpdate(TipoCfdiDTO tipoCfdiDTO);

    /**
     * Get all the tipoCfdis.
     *
     * @return the list of entities.
     */
    List<TipoCfdiDTO> findAll();

    /**
     * Get the "id" tipoCfdi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoCfdiDTO> findOne(Long id);

    /**
     * Delete the "id" tipoCfdi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
