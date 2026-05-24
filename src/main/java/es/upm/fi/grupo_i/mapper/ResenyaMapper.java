package es.upm.fi.grupo_i.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import es.upm.fi.grupo_i.dto.ResenyaDto;
import es.upm.fi.grupo_i.model.Resenya;

@Mapper(componentModel = "spring")
public abstract class ResenyaMapper {
    public abstract ResenyaDto toDto(Resenya resenya);

    public ResenyaDto toDto(Optional<Resenya> resenya) {
        return resenya.map(this::toDto).orElse(null);
    }
}
