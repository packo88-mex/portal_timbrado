package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Estados;
import com.mycompany.myapp.repository.EstadosRepository;
import com.mycompany.myapp.service.EstadosService;
import com.mycompany.myapp.service.dto.EstadosDTO;
import com.mycompany.myapp.service.mapper.EstadosMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Estados}.
 */
@Service
@Transactional
public class EstadosServiceImpl implements EstadosService {

    private final Logger log = LoggerFactory.getLogger(EstadosServiceImpl.class);

    private final EstadosRepository estadosRepository;

    private final EstadosMapper estadosMapper;

    public EstadosServiceImpl(EstadosRepository estadosRepository, EstadosMapper estadosMapper) {
        this.estadosRepository = estadosRepository;
        this.estadosMapper = estadosMapper;
    }

    @Override
    public EstadosDTO save(EstadosDTO estadosDTO) {
        log.debug("Request to save Estados : {}", estadosDTO);
        Estados estados = estadosMapper.toEntity(estadosDTO);
        estados = estadosRepository.save(estados);
        return estadosMapper.toDto(estados);
    }

    @Override
    public EstadosDTO update(EstadosDTO estadosDTO) {
        log.debug("Request to update Estados : {}", estadosDTO);
        Estados estados = estadosMapper.toEntity(estadosDTO);
        estados = estadosRepository.save(estados);
        return estadosMapper.toDto(estados);
    }

    @Override
    public Optional<EstadosDTO> partialUpdate(EstadosDTO estadosDTO) {
        log.debug("Request to partially update Estados : {}", estadosDTO);

        return estadosRepository
            .findById(estadosDTO.getId())
            .map(existingEstados -> {
                estadosMapper.partialUpdate(existingEstados, estadosDTO);

                return existingEstados;
            })
            .map(estadosRepository::save)
            .map(estadosMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstadosDTO> findAll() {
        log.debug("Request to get all Estados");
        return estadosRepository.findAll().stream().map(estadosMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstadosDTO> findOne(Long id) {
        log.debug("Request to get Estados : {}", id);
        return estadosRepository.findById(id).map(estadosMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estados : {}", id);
        estadosRepository.deleteById(id);
    }
}
