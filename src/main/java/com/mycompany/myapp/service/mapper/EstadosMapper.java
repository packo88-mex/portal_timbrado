package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Estados;
import com.mycompany.myapp.service.dto.EstadosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estados} and its DTO {@link EstadosDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstadosMapper extends EntityMapper<EstadosDTO, Estados> {}
