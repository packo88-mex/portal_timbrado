package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.RepresentacionGrafica;
import com.mycompany.myapp.domain.TipoRecibo;
import com.mycompany.myapp.service.dto.RepresentacionGraficaDTO;
import com.mycompany.myapp.service.dto.TipoReciboDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RepresentacionGrafica} and its DTO {@link RepresentacionGraficaDTO}.
 */
@Mapper(componentModel = "spring")
public interface RepresentacionGraficaMapper extends EntityMapper<RepresentacionGraficaDTO, RepresentacionGrafica> {
    @Mapping(target = "tiporecibo", source = "tiporecibo", qualifiedByName = "tipoReciboId")
    RepresentacionGraficaDTO toDto(RepresentacionGrafica s);

    @Named("tipoReciboId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoReciboDTO toDtoTipoReciboId(TipoRecibo tipoRecibo);
}
