package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EstatusCfdiDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.EstatusCfdi}.
 */
public interface EstatusCfdiService {
    /**
     * Save a estatusCfdi.
     *
     * @param estatusCfdiDTO the entity to save.
     * @return the persisted entity.
     */
    EstatusCfdiDTO save(EstatusCfdiDTO estatusCfdiDTO);

    /**
     * Updates a estatusCfdi.
     *
     * @param estatusCfdiDTO the entity to update.
     * @return the persisted entity.
     */
    EstatusCfdiDTO update(EstatusCfdiDTO estatusCfdiDTO);

    /**
     * Partially updates a estatusCfdi.
     *
     * @param estatusCfdiDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EstatusCfdiDTO> partialUpdate(EstatusCfdiDTO estatusCfdiDTO);

    /**
     * Get all the estatusCfdis.
     *
     * @return the list of entities.
     */
    List<EstatusCfdiDTO> findAll();

    /**
     * Get the "id" estatusCfdi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EstatusCfdiDTO> findOne(Long id);

    /**
     * Delete the "id" estatusCfdi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
