package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.EstatusCfdi;
import com.mycompany.myapp.service.dto.EstatusCfdiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstatusCfdi} and its DTO {@link EstatusCfdiDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstatusCfdiMapper extends EntityMapper<EstatusCfdiDTO, EstatusCfdi> {}
