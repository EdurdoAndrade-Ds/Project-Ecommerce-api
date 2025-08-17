package org.ecommerce.ecommerceapi.modules.client.mapper;

import org.ecommerce.ecommerceapi.modules.client.dto.UpdateClientDTO;
import org.ecommerce.ecommerceapi.modules.client.entities.ClientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {
    void updateFromDto(UpdateClientDTO dto, @MappingTarget ClientEntity entity);
}