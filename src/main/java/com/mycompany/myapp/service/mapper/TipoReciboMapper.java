package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TipoCfdi;
import com.mycompany.myapp.domain.TipoRecibo;
import com.mycompany.myapp.service.dto.TipoCfdiDTO;
import com.mycompany.myapp.service.dto.TipoReciboDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoRecibo} and its DTO {@link TipoReciboDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoReciboMapper extends EntityMapper<TipoReciboDTO, TipoRecibo> {
    @Mapping(target = "tipocfdi", source = "tipocfdi", qualifiedByName = "tipoCfdiId")
    TipoReciboDTO toDto(TipoRecibo s);

    @Named("tipoCfdiId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TipoCfdiDTO toDtoTipoCfdiId(TipoCfdi tipoCfdi);
}
