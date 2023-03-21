package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoRecibo;
import com.mycompany.myapp.repository.TipoReciboRepository;
import com.mycompany.myapp.service.TipoReciboService;
import com.mycompany.myapp.service.dto.TipoReciboDTO;
import com.mycompany.myapp.service.mapper.TipoReciboMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoRecibo}.
 */
@Service
@Transactional
public class TipoReciboServiceImpl implements TipoReciboService {

    private final Logger log = LoggerFactory.getLogger(TipoReciboServiceImpl.class);

    private final TipoReciboRepository tipoReciboRepository;

    private final TipoReciboMapper tipoReciboMapper;

    public TipoReciboServiceImpl(TipoReciboRepository tipoReciboRepository, TipoReciboMapper tipoReciboMapper) {
        this.tipoReciboRepository = tipoReciboRepository;
        this.tipoReciboMapper = tipoReciboMapper;
    }

    @Override
    public TipoReciboDTO save(TipoReciboDTO tipoReciboDTO) {
        log.debug("Request to save TipoRecibo : {}", tipoReciboDTO);
        TipoRecibo tipoRecibo = tipoReciboMapper.toEntity(tipoReciboDTO);
        tipoRecibo = tipoReciboRepository.save(tipoRecibo);
        return tipoReciboMapper.toDto(tipoRecibo);
    }

    @Override
    public TipoReciboDTO update(TipoReciboDTO tipoReciboDTO) {
        log.debug("Request to update TipoRecibo : {}", tipoReciboDTO);
        TipoRecibo tipoRecibo = tipoReciboMapper.toEntity(tipoReciboDTO);
        tipoRecibo = tipoReciboRepository.save(tipoRecibo);
        return tipoReciboMapper.toDto(tipoRecibo);
    }

    @Override
    public Optional<TipoReciboDTO> partialUpdate(TipoReciboDTO tipoReciboDTO) {
        log.debug("Request to partially update TipoRecibo : {}", tipoReciboDTO);

        return tipoReciboRepository
            .findById(tipoReciboDTO.getId())
            .map(existingTipoRecibo -> {
                tipoReciboMapper.partialUpdate(existingTipoRecibo, tipoReciboDTO);

                return existingTipoRecibo;
            })
            .map(tipoReciboRepository::save)
            .map(tipoReciboMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoReciboDTO> findAll() {
        log.debug("Request to get all TipoRecibos");
        return tipoReciboRepository.findAll().stream().map(tipoReciboMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoReciboDTO> findOne(Long id) {
        log.debug("Request to get TipoRecibo : {}", id);
        return tipoReciboRepository.findById(id).map(tipoReciboMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoRecibo : {}", id);
        tipoReciboRepository.deleteById(id);
    }
}
