package es.upm.fi.grupo_i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.grupo_i.model.Resenya;

public interface ResenyaRepository extends JpaRepository<Resenya, Long> {
    List<Resenya> findByAutorId(Long autorId);
}