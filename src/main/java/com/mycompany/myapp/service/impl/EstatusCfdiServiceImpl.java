package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.EstatusCfdi;
import com.mycompany.myapp.repository.EstatusCfdiRepository;
import com.mycompany.myapp.service.EstatusCfdiService;
import com.mycompany.myapp.service.dto.EstatusCfdiDTO;
import com.mycompany.myapp.service.mapper.EstatusCfdiMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstatusCfdi}.
 */
@Service
@Transactional
public class EstatusCfdiServiceImpl implements EstatusCfdiService {

    private final Logger log = LoggerFactory.getLogger(EstatusCfdiServiceImpl.class);

    private final EstatusCfdiRepository estatusCfdiRepository;

    private final EstatusCfdiMapper estatusCfdiMapper;

    public EstatusCfdiServiceImpl(EstatusCfdiRepository estatusCfdiRepository, EstatusCfdiMapper estatusCfdiMapper) {
        this.estatusCfdiRepository = estatusCfdiRepository;
        this.estatusCfdiMapper = estatusCfdiMapper;
    }

    @Override
    public EstatusCfdiDTO save(EstatusCfdiDTO estatusCfdiDTO) {
        log.debug("Request to save EstatusCfdi : {}", estatusCfdiDTO);
        EstatusCfdi estatusCfdi = estatusCfdiMapper.toEntity(estatusCfdiDTO);
        estatusCfdi = estatusCfdiRepository.save(estatusCfdi);
        return estatusCfdiMapper.toDto(estatusCfdi);
    }

    @Override
    public EstatusCfdiDTO update(EstatusCfdiDTO estatusCfdiDTO) {
        log.debug("Request to update EstatusCfdi : {}", estatusCfdiDTO);
        EstatusCfdi estatusCfdi = estatusCfdiMapper.toEntity(estatusCfdiDTO);
        estatusCfdi = estatusCfdiRepository.save(estatusCfdi);
        return estatusCfdiMapper.toDto(estatusCfdi);
    }

    @Override
    public Optional<EstatusCfdiDTO> partialUpdate(EstatusCfdiDTO estatusCfdiDTO) {
        log.debug("Request to partially update EstatusCfdi : {}", estatusCfdiDTO);

        return estatusCfdiRepository
            .findById(estatusCfdiDTO.getId())
            .map(existingEstatusCfdi -> {
                estatusCfdiMapper.partialUpdate(existingEstatusCfdi, estatusCfdiDTO);

                return existingEstatusCfdi;
            })
            .map(estatusCfdiRepository::save)
            .map(estatusCfdiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EstatusCfdiDTO> findAll() {
        log.debug("Request to get all EstatusCfdis");
        return estatusCfdiRepository.findAll().stream().map(estatusCfdiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EstatusCfdiDTO> findOne(Long id) {
        log.debug("Request to get EstatusCfdi : {}", id);
        return estatusCfdiRepository.findById(id).map(estatusCfdiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EstatusCfdi : {}", id);
        estatusCfdiRepository.deleteById(id);
    }
}
