package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.MotivosCancelacion;
import com.mycompany.myapp.service.dto.MotivosCancelacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link MotivosCancelacion} and its DTO {@link MotivosCancelacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface MotivosCancelacionMapper extends EntityMapper<MotivosCancelacionDTO, MotivosCancelacion> {}
