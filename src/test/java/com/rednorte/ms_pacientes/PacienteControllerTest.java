package com.rednorte.ms_pacientes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PacienteService pacienteService;

    @InjectMocks
    private PacienteController pacienteController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(pacienteController).build();
    }

    @Test
    void listarPacientesShouldReturnOk() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Test");
        when(pacienteService.findAll()).thenReturn(List.of(paciente));

        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Test"));
    }

    @Test
    void obtenerPacienteShouldReturnOkWhenFound() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNombre("Test");
        when(pacienteService.findById(1L)).thenReturn(Optional.of(paciente));

        mockMvc.perform(get("/api/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void crearPacienteShouldReturnCreated() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("Nuevo");
        when(pacienteService.save(any(Paciente.class))).thenReturn(paciente);

        mockMvc.perform(post("/api/pacientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nuevo"));
    }

    @Test
    void actualizarPacienteShouldReturnOkWhenFound() throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNombre("Actualizado");
        when(pacienteService.update(eq(1L), any(Paciente.class))).thenReturn(Optional.of(paciente));

        mockMvc.perform(put("/api/pacientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizado"));
    }

    @Test
    void eliminarPacienteShouldReturnNoContentWhenDeleted() throws Exception {
        when(pacienteService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/pacientes/1"))
                .andExpect(status().isNoContent());
    }
}
