package es.upm.fi.grupo_i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.grupo_i.model.Reserva;

public interface ReservasRepository extends JpaRepository<Reserva, Long> {
    Page<Reserva> findById(Long id, Pageable pageable);
}
