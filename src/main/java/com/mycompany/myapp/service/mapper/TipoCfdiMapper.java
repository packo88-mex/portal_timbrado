package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TipoCfdi;
import com.mycompany.myapp.service.dto.TipoCfdiDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoCfdi} and its DTO {@link TipoCfdiDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoCfdiMapper extends EntityMapper<TipoCfdiDTO, TipoCfdi> {}
