package es.upm.fi.grupo_i.mapper;

import es.upm.fi.grupo_i.dto.ReservaDto;
import es.upm.fi.grupo_i.model.Reserva;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ReservaMapper {

    //Se conectan tipos y nombres de las variables entre Reserva y ReservaDto
    public abstract ReservaDto toDto(Reserva reserva);

    //Se trata de obtener el Viaje del repositorio. Si no existe, se devuelve null y se evita error
    public ReservaDto toDto(Optional<Reserva> reserva) {
        return reserva.map(this::toDto).orElse(null);
    }

    //Se realiza el mapeo de lista de Reserva a lista de ReservaDtos
    public abstract List<ReservaDto> toDtoList(List<Reserva> reservas);

}