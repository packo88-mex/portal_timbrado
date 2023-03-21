package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RegimenFiscalDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.RegimenFiscal}.
 */
public interface RegimenFiscalService {
    /**
     * Save a regimenFiscal.
     *
     * @param regimenFiscalDTO the entity to save.
     * @return the persisted entity.
     */
    RegimenFiscalDTO save(RegimenFiscalDTO regimenFiscalDTO);

    /**
     * Updates a regimenFiscal.
     *
     * @param regimenFiscalDTO the entity to update.
     * @return the persisted entity.
     */
    RegimenFiscalDTO update(RegimenFiscalDTO regimenFiscalDTO);

    /**
     * Partially updates a regimenFiscal.
     *
     * @param regimenFiscalDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RegimenFiscalDTO> partialUpdate(RegimenFiscalDTO regimenFiscalDTO);

    /**
     * Get all the regimenFiscals.
     *
     * @return the list of entities.
     */
    List<RegimenFiscalDTO> findAll();

    /**
     * Get the "id" regimenFiscal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RegimenFiscalDTO> findOne(Long id);

    /**
     * Delete the "id" regimenFiscal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
