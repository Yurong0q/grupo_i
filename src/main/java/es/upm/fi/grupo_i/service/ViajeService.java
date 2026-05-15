package es.upm.fi.grupo_i.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import es.upm.fi.grupo_i.model.Viaje;
// import es.upm.fi.grupo_i.repository.ViajeRepository;
import es.upm.fi.grupo_i.repository.ViajeRepository;

@Service
public class ViajeService {

    private final ViajeRepository ViajeRepository;

    public ViajeService(ViajeRepository ViajeRepository) {
        this.ViajeRepository = ViajeRepository;
    }
}