package com.rednorte.ms_pacientes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    @Test
    void findAllShouldReturnPacienteList() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Test");
        when(pacienteRepository.findAll()).thenReturn(List.of(paciente));

        List<Paciente> result = pacienteService.findAll();

        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getNombre());
    }

    @Test
    void findByIdShouldReturnPacienteWhenFound() {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));

        Optional<Paciente> result = pacienteService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void saveShouldPersistPaciente() {
        Paciente paciente = new Paciente();
        paciente.setNombre("Nuevo");
        when(pacienteRepository.save(paciente)).thenReturn(paciente);

        Paciente result = pacienteService.save(paciente);

        assertNotNull(result);
        assertEquals("Nuevo", result.getNombre());
        verify(pacienteRepository).save(paciente);
    }

    @Test
    void updateShouldModifyExistingPaciente() {
        Paciente existing = new Paciente();
        existing.setId(1L);
        existing.setNombre("Original");
        existing.setRut("11111111-1");

        Paciente updated = new Paciente();
        updated.setNombre("Actualizado");
        updated.setRut("22222222-2");

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(pacienteRepository.save(existing)).thenReturn(existing);

        Optional<Paciente> result = pacienteService.update(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Actualizado", result.get().getNombre());
        assertEquals("22222222-2", result.get().getRut());
    }

    @Test
    void updateShouldReturnEmptyWhenPacienteDoesNotExist() {
        when(pacienteRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Paciente> result = pacienteService.update(1L, new Paciente());

        assertTrue(result.isEmpty());
    }

    @Test
    void deleteShouldReturnTrueWhenPacienteExists() {
        when(pacienteRepository.existsById(1L)).thenReturn(true);

        boolean deleted = pacienteService.delete(1L);

        assertTrue(deleted);
        verify(pacienteRepository).deleteById(1L);
    }

    @Test
    void deleteShouldReturnFalseWhenPacienteDoesNotExist() {
        when(pacienteRepository.existsById(1L)).thenReturn(false);

        boolean deleted = pacienteService.delete(1L);

        assertFalse(deleted);
        verify(pacienteRepository, never()).deleteById(anyLong());
    }
}
