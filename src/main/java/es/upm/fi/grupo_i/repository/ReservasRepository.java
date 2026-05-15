package es.upm.fi.grupo_i.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.grupo_i.model.Reserva;

public interface ReservasRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByViajeId(Long viajeId);
}