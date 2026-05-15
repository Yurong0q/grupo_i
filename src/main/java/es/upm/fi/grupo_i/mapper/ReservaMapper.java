package es.upm.fi.grupo_i.mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import es.upm.fi.grupo_i.dto.ReservaDto;
import es.upm.fi.grupo_i.model.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    ReservaDto toDto(Reserva reserva);
    List<ReservaDto> toDtoList(List<Reserva> reservas);
}