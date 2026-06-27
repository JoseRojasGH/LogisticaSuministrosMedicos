package cl.duoc.Cliente.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.service.ClienteService;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setup() {
        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setRut("12345678-9");
        clienteEjemplo.setNombre("Roberto Salinas");
        clienteEjemplo.setDireccion("Avenida Siempre Viva 123");
        clienteEjemplo.setCorreo("roberto@gmail.com");
    }

    // @GET

    @Test
    void listarClientes_retorna200() throws Exception {
        when(clienteService.listarClientes()).thenReturn(List.of(clienteEjemplo));

        llamadaFalsa.perform(get("/api/v1/clientes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Roberto Salinas"));
    }

    @Test
    void listarClientes_retorna204() throws Exception {
        when(clienteService.listarClientes()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/clientes"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void getClienteById_retorna200() throws Exception {
        when(clienteService.buscarClientePorId(1)).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(get("/api/v1/clientes/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Roberto Salinas"));
    }

    @Test
    void getClienteById_retorna404() throws Exception {
        when(clienteService.buscarClientePorId(99))
            .thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(get("/api/v1/clientes/id/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por RUT

    @Test
    void getClienteByRut_retorna200() throws Exception {
        when(clienteService.buscarClientePorRut("12345678-9")).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(get("/api/v1/clientes/rut/12345678-9"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void getClienteByRut_retorna404() throws Exception {
        when(clienteService.buscarClientePorRut("00000000-0"))
            .thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(get("/api/v1/clientes/rut/00000000-0"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void createCliente_retorna200() throws Exception {
        when(clienteService.crearCliente(any(Cliente.class))).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(post("/api/v1/clientes")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "12345678-9",
                        "nombre": "Roberto Salinas",
                        "direccion": "Avenida Siempre Viva 123",
                        "correo": "roberto@gmail.com"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Roberto Salinas"));
    }

    // @PUT

    @Test
    void updateCliente_retorna200() throws Exception {
        when(clienteService.actualizarCliente(eq(1), any(Cliente.class))).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(put("/api/v1/clientes/1")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "98765432-1",
                        "nombre": "Ana González",
                        "direccion": "Calle Nueva 456",
                        "correo": "ana@gmail.com"
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateCliente_retorna404() throws Exception {
        when(clienteService.actualizarCliente(eq(99), any(Cliente.class)))
            .thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(put("/api/v1/clientes/99")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "98765432-1",
                        "nombre": "Ana González",
                        "direccion": "Calle Nueva 456",
                        "correo": "ana@gmail.com"
                    }
                """))
            .andExpect(status().isNotFound());
    }
}
