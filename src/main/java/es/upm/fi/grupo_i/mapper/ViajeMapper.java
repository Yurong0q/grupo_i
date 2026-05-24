package es.upm.fi.grupo_i.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.upm.fi.grupo_i.dto.ViajeCreateDto;
import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;

@Mapper(componentModel = "spring")
public interface ViajeMapper {

    ViajeDto toDto(Viaje viaje);

    List<ViajeDto> toDtoList(List<Viaje> viajes);

    Viaje toEntity(ViajeCreateDto dto);
}