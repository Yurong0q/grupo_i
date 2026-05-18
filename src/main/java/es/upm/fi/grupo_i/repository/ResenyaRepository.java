package es.upm.fi.grupo_i.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.grupo_i.model.Resenya;

public interface ResenyaRepository extends JpaRepository<Resenya, Long> {
    Page<Resenya> findByAutorId(Long autorId, Pageable pageable);
}