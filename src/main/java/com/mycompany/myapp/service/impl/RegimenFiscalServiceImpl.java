package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.RegimenFiscal;
import com.mycompany.myapp.repository.RegimenFiscalRepository;
import com.mycompany.myapp.service.RegimenFiscalService;
import com.mycompany.myapp.service.dto.RegimenFiscalDTO;
import com.mycompany.myapp.service.mapper.RegimenFiscalMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RegimenFiscal}.
 */
@Service
@Transactional
public class RegimenFiscalServiceImpl implements RegimenFiscalService {

    private final Logger log = LoggerFactory.getLogger(RegimenFiscalServiceImpl.class);

    private final RegimenFiscalRepository regimenFiscalRepository;

    private final RegimenFiscalMapper regimenFiscalMapper;

    public RegimenFiscalServiceImpl(RegimenFiscalRepository regimenFiscalRepository, RegimenFiscalMapper regimenFiscalMapper) {
        this.regimenFiscalRepository = regimenFiscalRepository;
        this.regimenFiscalMapper = regimenFiscalMapper;
    }

    @Override
    public RegimenFiscalDTO save(RegimenFiscalDTO regimenFiscalDTO) {
        log.debug("Request to save RegimenFiscal : {}", regimenFiscalDTO);
        RegimenFiscal regimenFiscal = regimenFiscalMapper.toEntity(regimenFiscalDTO);
        regimenFiscal = regimenFiscalRepository.save(regimenFiscal);
        return regimenFiscalMapper.toDto(regimenFiscal);
    }

    @Override
    public RegimenFiscalDTO update(RegimenFiscalDTO regimenFiscalDTO) {
        log.debug("Request to update RegimenFiscal : {}", regimenFiscalDTO);
        RegimenFiscal regimenFiscal = regimenFiscalMapper.toEntity(regimenFiscalDTO);
        regimenFiscal = regimenFiscalRepository.save(regimenFiscal);
        return regimenFiscalMapper.toDto(regimenFiscal);
    }

    @Override
    public Optional<RegimenFiscalDTO> partialUpdate(RegimenFiscalDTO regimenFiscalDTO) {
        log.debug("Request to partially update RegimenFiscal : {}", regimenFiscalDTO);

        return regimenFiscalRepository
            .findById(regimenFiscalDTO.getId())
            .map(existingRegimenFiscal -> {
                regimenFiscalMapper.partialUpdate(existingRegimenFiscal, regimenFiscalDTO);

                return existingRegimenFiscal;
            })
            .map(regimenFiscalRepository::save)
            .map(regimenFiscalMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegimenFiscalDTO> findAll() {
        log.debug("Request to get all RegimenFiscals");
        return regimenFiscalRepository.findAll().stream().map(regimenFiscalMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RegimenFiscalDTO> findOne(Long id) {
        log.debug("Request to get RegimenFiscal : {}", id);
        return regimenFiscalRepository.findById(id).map(regimenFiscalMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RegimenFiscal : {}", id);
        regimenFiscalRepository.deleteById(id);
    }
}
