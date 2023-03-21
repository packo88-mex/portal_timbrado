package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.TipoCfdi;
import com.mycompany.myapp.repository.TipoCfdiRepository;
import com.mycompany.myapp.service.TipoCfdiService;
import com.mycompany.myapp.service.dto.TipoCfdiDTO;
import com.mycompany.myapp.service.mapper.TipoCfdiMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TipoCfdi}.
 */
@Service
@Transactional
public class TipoCfdiServiceImpl implements TipoCfdiService {

    private final Logger log = LoggerFactory.getLogger(TipoCfdiServiceImpl.class);

    private final TipoCfdiRepository tipoCfdiRepository;

    private final TipoCfdiMapper tipoCfdiMapper;

    public TipoCfdiServiceImpl(TipoCfdiRepository tipoCfdiRepository, TipoCfdiMapper tipoCfdiMapper) {
        this.tipoCfdiRepository = tipoCfdiRepository;
        this.tipoCfdiMapper = tipoCfdiMapper;
    }

    @Override
    public TipoCfdiDTO save(TipoCfdiDTO tipoCfdiDTO) {
        log.debug("Request to save TipoCfdi : {}", tipoCfdiDTO);
        TipoCfdi tipoCfdi = tipoCfdiMapper.toEntity(tipoCfdiDTO);
        tipoCfdi = tipoCfdiRepository.save(tipoCfdi);
        return tipoCfdiMapper.toDto(tipoCfdi);
    }

    @Override
    public TipoCfdiDTO update(TipoCfdiDTO tipoCfdiDTO) {
        log.debug("Request to update TipoCfdi : {}", tipoCfdiDTO);
        TipoCfdi tipoCfdi = tipoCfdiMapper.toEntity(tipoCfdiDTO);
        tipoCfdi = tipoCfdiRepository.save(tipoCfdi);
        return tipoCfdiMapper.toDto(tipoCfdi);
    }

    @Override
    public Optional<TipoCfdiDTO> partialUpdate(TipoCfdiDTO tipoCfdiDTO) {
        log.debug("Request to partially update TipoCfdi : {}", tipoCfdiDTO);

        return tipoCfdiRepository
            .findById(tipoCfdiDTO.getId())
            .map(existingTipoCfdi -> {
                tipoCfdiMapper.partialUpdate(existingTipoCfdi, tipoCfdiDTO);

                return existingTipoCfdi;
            })
            .map(tipoCfdiRepository::save)
            .map(tipoCfdiMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TipoCfdiDTO> findAll() {
        log.debug("Request to get all TipoCfdis");
        return tipoCfdiRepository.findAll().stream().map(tipoCfdiMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TipoCfdiDTO> findOne(Long id) {
        log.debug("Request to get TipoCfdi : {}", id);
        return tipoCfdiRepository.findById(id).map(tipoCfdiMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoCfdi : {}", id);
        tipoCfdiRepository.deleteById(id);
    }
}
