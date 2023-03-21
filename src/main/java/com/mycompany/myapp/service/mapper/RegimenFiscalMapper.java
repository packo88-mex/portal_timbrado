package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.RegimenFiscal;
import com.mycompany.myapp.service.dto.RegimenFiscalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RegimenFiscal} and its DTO {@link RegimenFiscalDTO}.
 */
@Mapper(componentModel = "spring")
public interface RegimenFiscalMapper extends EntityMapper<RegimenFiscalDTO, RegimenFiscal> {}
