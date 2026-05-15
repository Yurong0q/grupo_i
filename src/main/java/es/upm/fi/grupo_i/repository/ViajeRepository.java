package es.upm.fi.grupo_i.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.grupo_i.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
    Page<Viaje> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}