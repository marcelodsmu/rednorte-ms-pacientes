package com.rednorte.ms_pacientes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// AQUÍ ESTÁ APLICADO EL PATRÓN REPOSITORY
@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}