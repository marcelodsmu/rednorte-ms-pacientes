package com.rednorte.ms_pacientes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsPacientesApplicationTests {

    @Test
    void verificarCreacionDePaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Marcelo San Martin");
        
        // Evidencia de prueba unitaria exitosa
        assertNotNull(paciente.getNombre(), "El nombre del paciente no deberia ser nulo");
    }
}