package es.upm.fi.grupo_i.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.upm.fi.grupo_i.dto.ViajeCreateDto;
import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;

@Mapper(componentModel = "spring")
public abstract class ViajeMapper {

    public abstract ViajeDto toDto(Viaje viaje);

    public abstract List<ViajeDto> toDtoList(List<Viaje> viajes);

    public abstract Viaje toEntity(ViajeCreateDto dto);
    
}