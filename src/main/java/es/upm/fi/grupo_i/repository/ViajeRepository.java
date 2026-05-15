package es.upm.fi.grupo_i.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.fi.grupo_i.model.Viaje;

public interface ViajeRepository extends JpaRepository<Viaje, Long> {
}
