package com.rednorte.ms_pacientes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> findAll() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return pacienteRepository.findById(id);
    }

    public Paciente save(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public Optional<Paciente> update(Long id, Paciente paciente) {
        return pacienteRepository.findById(id)
                .map(existing -> {
                    existing.setNombre(paciente.getNombre());
                    existing.setRut(paciente.getRut());
                    return pacienteRepository.save(existing);
                });
    }

    public boolean delete(Long id) {
        if (pacienteRepository.existsById(id)) {
            pacienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
