package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.MotivosCancelacion;
import com.mycompany.myapp.repository.MotivosCancelacionRepository;
import com.mycompany.myapp.service.MotivosCancelacionService;
import com.mycompany.myapp.service.dto.MotivosCancelacionDTO;
import com.mycompany.myapp.service.mapper.MotivosCancelacionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MotivosCancelacion}.
 */
@Service
@Transactional
public class MotivosCancelacionServiceImpl implements MotivosCancelacionService {

    private final Logger log = LoggerFactory.getLogger(MotivosCancelacionServiceImpl.class);

    private final MotivosCancelacionRepository motivosCancelacionRepository;

    private final MotivosCancelacionMapper motivosCancelacionMapper;

    public MotivosCancelacionServiceImpl(
        MotivosCancelacionRepository motivosCancelacionRepository,
        MotivosCancelacionMapper motivosCancelacionMapper
    ) {
        this.motivosCancelacionRepository = motivosCancelacionRepository;
        this.motivosCancelacionMapper = motivosCancelacionMapper;
    }

    @Override
    public MotivosCancelacionDTO save(MotivosCancelacionDTO motivosCancelacionDTO) {
        log.debug("Request to save MotivosCancelacion : {}", motivosCancelacionDTO);
        MotivosCancelacion motivosCancelacion = motivosCancelacionMapper.toEntity(motivosCancelacionDTO);
        motivosCancelacion = motivosCancelacionRepository.save(motivosCancelacion);
        return motivosCancelacionMapper.toDto(motivosCancelacion);
    }

    @Override
    public MotivosCancelacionDTO update(MotivosCancelacionDTO motivosCancelacionDTO) {
        log.debug("Request to update MotivosCancelacion : {}", motivosCancelacionDTO);
        MotivosCancelacion motivosCancelacion = motivosCancelacionMapper.toEntity(motivosCancelacionDTO);
        motivosCancelacion = motivosCancelacionRepository.save(motivosCancelacion);
        return motivosCancelacionMapper.toDto(motivosCancelacion);
    }

    @Override
    public Optional<MotivosCancelacionDTO> partialUpdate(MotivosCancelacionDTO motivosCancelacionDTO) {
        log.debug("Request to partially update MotivosCancelacion : {}", motivosCancelacionDTO);

        return motivosCancelacionRepository
            .findById(motivosCancelacionDTO.getId())
            .map(existingMotivosCancelacion -> {
                motivosCancelacionMapper.partialUpdate(existingMotivosCancelacion, motivosCancelacionDTO);

                return existingMotivosCancelacion;
            })
            .map(motivosCancelacionRepository::save)
            .map(motivosCancelacionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MotivosCancelacionDTO> findAll() {
        log.debug("Request to get all MotivosCancelacions");
        return motivosCancelacionRepository
            .findAll()
            .stream()
            .map(motivosCancelacionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MotivosCancelacionDTO> findOne(Long id) {
        log.debug("Request to get MotivosCancelacion : {}", id);
        return motivosCancelacionRepository.findById(id).map(motivosCancelacionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MotivosCancelacion : {}", id);
        motivosCancelacionRepository.deleteById(id);
    }
}
