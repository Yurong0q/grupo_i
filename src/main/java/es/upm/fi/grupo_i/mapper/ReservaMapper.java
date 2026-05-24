package es.upm.fi.grupo_i.mapper;

import es.upm.fi.grupo_i.dto.ReservaDto;
import es.upm.fi.grupo_i.dto.ViajeDto;
import es.upm.fi.grupo_i.model.Reserva;
import es.upm.fi.grupo_i.model.Viaje;
import es.upm.fi.grupo_i.service.ReservaService;
import es.upm.fi.grupo_i.service.ViajeService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class ReservaMapper {

    @Autowired
    protected ReservaService reservaService;

    //Se conectan tipos y nombres de las variables entre Reserva y ReservaDto

    public abstract ReservaDto toDto(Reserva reserva);

    //Se trata de obtener el Reserva del repositorio. Si no existe, se devuelve null y se evita error
    public ReservaDto toDto(Optional<Reserva> reserva) {
        return reserva.map(this::toDto).orElse(null);
    }

    //Se realiza el mapeo de lista de Reserva a lista de ReservaDtos
    public abstract List<ReservaDto> toDtoList(List<Reserva> reserva);

}
