package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MotivosCancelacionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.MotivosCancelacion}.
 */
public interface MotivosCancelacionService {
    /**
     * Save a motivosCancelacion.
     *
     * @param motivosCancelacionDTO the entity to save.
     * @return the persisted entity.
     */
    MotivosCancelacionDTO save(MotivosCancelacionDTO motivosCancelacionDTO);

    /**
     * Updates a motivosCancelacion.
     *
     * @param motivosCancelacionDTO the entity to update.
     * @return the persisted entity.
     */
    MotivosCancelacionDTO update(MotivosCancelacionDTO motivosCancelacionDTO);

    /**
     * Partially updates a motivosCancelacion.
     *
     * @param motivosCancelacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MotivosCancelacionDTO> partialUpdate(MotivosCancelacionDTO motivosCancelacionDTO);

    /**
     * Get all the motivosCancelacions.
     *
     * @return the list of entities.
     */
    List<MotivosCancelacionDTO> findAll();

    /**
     * Get the "id" motivosCancelacion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MotivosCancelacionDTO> findOne(Long id);

    /**
     * Delete the "id" motivosCancelacion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
