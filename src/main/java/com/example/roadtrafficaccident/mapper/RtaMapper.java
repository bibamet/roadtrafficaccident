package com.example.roadtrafficaccident.mapper;

import com.example.roadtrafficaccident.dto.DriverDTO;
import com.example.roadtrafficaccident.dto.RtaDto;
import com.example.roadtrafficaccident.entity.RTAEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface RtaMapper {

    RTAEntity toRTAEntity(RtaDto addresAndRTADTO);
    RtaDto toRtaDto(RTAEntity rtaEntity);
    void updateRTAEntity(DriverDTO source, @MappingTarget RTAEntity target);

}
