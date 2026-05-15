package es.upm.fi.grupo_i.mapper;

import es.upm.fi.grupo_i.GrupoIApplication;
import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ViajeMapper {
    @Autowired
    protected GrupoIApplication grupoIApplication;

    public abstract ViajeDto toDto(Viaje viaje);

    public ViajeDto toDto(Optional<Viaje> viaje) {
        return viaje.map(this::toDto).orElse(null);
    }

    public abstract List<ViajeDto> toDtoList(List<Viaje> productos);
}