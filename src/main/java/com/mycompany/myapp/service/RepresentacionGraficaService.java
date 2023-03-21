package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.RepresentacionGraficaDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.RepresentacionGrafica}.
 */
public interface RepresentacionGraficaService {
    /**
     * Save a representacionGrafica.
     *
     * @param representacionGraficaDTO the entity to save.
     * @return the persisted entity.
     */
    RepresentacionGraficaDTO save(RepresentacionGraficaDTO representacionGraficaDTO);

    /**
     * Updates a representacionGrafica.
     *
     * @param representacionGraficaDTO the entity to update.
     * @return the persisted entity.
     */
    RepresentacionGraficaDTO update(RepresentacionGraficaDTO representacionGraficaDTO);

    /**
     * Partially updates a representacionGrafica.
     *
     * @param representacionGraficaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RepresentacionGraficaDTO> partialUpdate(RepresentacionGraficaDTO representacionGraficaDTO);

    /**
     * Get all the representacionGraficas.
     *
     * @return the list of entities.
     */
    List<RepresentacionGraficaDTO> findAll();

    /**
     * Get the "id" representacionGrafica.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RepresentacionGraficaDTO> findOne(Long id);

    /**
     * Delete the "id" representacionGrafica.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
