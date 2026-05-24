package es.upm.fi.grupo_i.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import es.upm.fi.grupo_i.dto.ViajeCreateDto;
import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;

@Mapper(componentModel = "spring")
public abstract class ViajeMapper {

    public abstract ViajeDto toDto(Viaje viaje);

    public ViajeDto toDto(Optional<Viaje> viaje) {
        return viaje.map(this::toDto).orElse(null);
    }

    public abstract Viaje toEntity(ViajeCreateDto dto);
    
}