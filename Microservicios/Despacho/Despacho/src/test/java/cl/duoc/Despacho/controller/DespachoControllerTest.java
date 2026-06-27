package cl.duoc.Despacho.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.Despacho.model.Despacho;
import cl.duoc.Despacho.service.DespachoService;

@WebMvcTest(DespachoController.class)
public class DespachoControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private DespachoService service;

    private Despacho despachoEjemplo;

    @BeforeEach
    void setup() {
        despachoEjemplo = new Despacho();
        despachoEjemplo.setId(1);
        despachoEjemplo.setNombreConductor("Juan Pérez");
        despachoEjemplo.setFechaEntrega(new Date());
        despachoEjemplo.setClienteId(1);
    }

    // @GET

    @Test
    void listar_retorna200() throws Exception {
        when(service.listar()).thenReturn(List.of(despachoEjemplo));

        llamadaFalsa.perform(get("/api/v1/despachos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombreConductor").value("Juan Pérez"));
    }

    @Test
    void listar_retorna204() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/despachos"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(despachoEjemplo);

        llamadaFalsa.perform(get("/api/v1/despachos/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombreConductor").value("Juan Pérez"));
    }

    @Test
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Despacho no encontrado"));

        llamadaFalsa.perform(get("/api/v1/despachos/id/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Cliente

    @Test
    void buscarPorCliente_retorna200() throws Exception {
        when(service.buscarPorCliente(1)).thenReturn(List.of(despachoEjemplo));

        llamadaFalsa.perform(get("/api/v1/despachos/cliente/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].clienteId").value(1));
    }

    @Test
    void buscarPorCliente_retorna404() throws Exception {
        when(service.buscarPorCliente(99)).thenThrow(new RuntimeException("No se encontraron despachos para el cliente"));

        llamadaFalsa.perform(get("/api/v1/despachos/cliente/99"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Despacho.class))).thenReturn(despachoEjemplo);

        llamadaFalsa.perform(post("/api/v1/despachos")
                .contentType("application/json")
                .content("""
                    {
                        "nombreConductor": "Juan Pérez",
                        "fechaEntrega": "2025-12-01",
                        "clienteId": 1
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombreConductor").value("Juan Pérez"));
    }

    @Test
    void guardar_retorna400_cuandoClienteNoExiste() throws Exception {
        when(service.guardar(any(Despacho.class)))
            .thenThrow(new RuntimeException("Cliente no existe"));

        llamadaFalsa.perform(post("/api/v1/despachos")
                .contentType("application/json")
                .content("""
                    {
                        "nombreConductor": "Juan Pérez",
                        "fechaEntrega": "2025-12-01",
                        "clienteId": 99
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // @PUT

    @Test
    void actualizar_retorna200() throws Exception {
        when(service.actualizar(eq(1), any(Despacho.class))).thenReturn(despachoEjemplo);

        llamadaFalsa.perform(put("/api/v1/despachos/1")
                .contentType("application/json")
                .content("""
                    {
                        "nombreConductor": "Pedro González",
                        "fechaEntrega": "2025-12-15",
                        "clienteId": 2
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizar_retorna404_cuandoNoExiste() throws Exception {
        when(service.actualizar(eq(99), any(Despacho.class)))
            .thenThrow(new RuntimeException("Despacho no encontrado"));

        llamadaFalsa.perform(put("/api/v1/despachos/99")
                .contentType("application/json")
                .content("""
                    {
                        "nombreConductor": "Pedro González",
                        "fechaEntrega": "2025-12-15",
                        "clienteId": 2
                    }
                """))
            .andExpect(status().isNotFound());
    }

    // @DELETE

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        llamadaFalsa.perform(delete("/api/v1/despachos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404_cuandoNoExiste() throws Exception {
        doThrow(new RuntimeException("Despacho no encontrado")).when(service).eliminar(99);

        llamadaFalsa.perform(delete("/api/v1/despachos/99"))
            .andExpect(status().isNotFound());
    }
}