package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.RepresentacionGrafica;
import com.mycompany.myapp.repository.RepresentacionGraficaRepository;
import com.mycompany.myapp.service.RepresentacionGraficaService;
import com.mycompany.myapp.service.dto.RepresentacionGraficaDTO;
import com.mycompany.myapp.service.mapper.RepresentacionGraficaMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RepresentacionGrafica}.
 */
@Service
@Transactional
public class RepresentacionGraficaServiceImpl implements RepresentacionGraficaService {

    private final Logger log = LoggerFactory.getLogger(RepresentacionGraficaServiceImpl.class);

    private final RepresentacionGraficaRepository representacionGraficaRepository;

    private final RepresentacionGraficaMapper representacionGraficaMapper;

    public RepresentacionGraficaServiceImpl(
        RepresentacionGraficaRepository representacionGraficaRepository,
        RepresentacionGraficaMapper representacionGraficaMapper
    ) {
        this.representacionGraficaRepository = representacionGraficaRepository;
        this.representacionGraficaMapper = representacionGraficaMapper;
    }

    @Override
    public RepresentacionGraficaDTO save(RepresentacionGraficaDTO representacionGraficaDTO) {
        log.debug("Request to save RepresentacionGrafica : {}", representacionGraficaDTO);
        RepresentacionGrafica representacionGrafica = representacionGraficaMapper.toEntity(representacionGraficaDTO);
        representacionGrafica = representacionGraficaRepository.save(representacionGrafica);
        return representacionGraficaMapper.toDto(representacionGrafica);
    }

    @Override
    public RepresentacionGraficaDTO update(RepresentacionGraficaDTO representacionGraficaDTO) {
        log.debug("Request to update RepresentacionGrafica : {}", representacionGraficaDTO);
        RepresentacionGrafica representacionGrafica = representacionGraficaMapper.toEntity(representacionGraficaDTO);
        representacionGrafica = representacionGraficaRepository.save(representacionGrafica);
        return representacionGraficaMapper.toDto(representacionGrafica);
    }

    @Override
    public Optional<RepresentacionGraficaDTO> partialUpdate(RepresentacionGraficaDTO representacionGraficaDTO) {
        log.debug("Request to partially update RepresentacionGrafica : {}", representacionGraficaDTO);

        return representacionGraficaRepository
            .findById(representacionGraficaDTO.getId())
            .map(existingRepresentacionGrafica -> {
                representacionGraficaMapper.partialUpdate(existingRepresentacionGrafica, representacionGraficaDTO);

                return existingRepresentacionGrafica;
            })
            .map(representacionGraficaRepository::save)
            .map(representacionGraficaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RepresentacionGraficaDTO> findAll() {
        log.debug("Request to get all RepresentacionGraficas");
        return representacionGraficaRepository
            .findAll()
            .stream()
            .map(representacionGraficaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RepresentacionGraficaDTO> findOne(Long id) {
        log.debug("Request to get RepresentacionGrafica : {}", id);
        return representacionGraficaRepository.findById(id).map(representacionGraficaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RepresentacionGrafica : {}", id);
        representacionGraficaRepository.deleteById(id);
    }
}
