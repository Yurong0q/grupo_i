package es.upm.fi.grupo_i.mapper;

import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.service.ViajeService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ViajeMapper {

    @Autowired
    protected ViajeService viajeService;

    //Se conectan tipos y nombres de las variables entre Viaje y ViajeDto

    public abstract ViajeDto toDto(Viaje viaje);

    //Se trata de obtener el Viaje del repositorio. Si no existe, se devuelve null y se evita error
    public ViajeDto toDto(Optional<Viaje> viaje) {
        return viaje.map(this::toDto).orElse(null);
    }

    //Se realiza el mapeo de lista de Viajes a lista de ViajeDtos
    public abstract List<ViajeDto> toDtoList(List<Viaje> viajes);

}