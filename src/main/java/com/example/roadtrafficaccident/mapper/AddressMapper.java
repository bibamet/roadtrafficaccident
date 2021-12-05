package com.example.roadtrafficaccident.mapper;

import com.example.roadtrafficaccident.dto.AddressDto;
import com.example.roadtrafficaccident.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {
    AddressEntity toAddressEntity(AddressDto addressDto);
}
