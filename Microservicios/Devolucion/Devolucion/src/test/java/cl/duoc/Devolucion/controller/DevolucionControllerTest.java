package cl.duoc.Devolucion.controller;

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

import cl.duoc.Devolucion.model.Devolucion;
import cl.duoc.Devolucion.service.DevolucionService;

@WebMvcTest(DevolucionController.class)
public class DevolucionControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private DevolucionService devolucionService;

    private Devolucion devolucionEjemplo;

    @BeforeEach
    void setup() {
        devolucionEjemplo = new Devolucion();
        devolucionEjemplo.setId(1);
        devolucionEjemplo.setFechaDevolucion(new Date());
        devolucionEjemplo.setMotivo("Producto dañado");
        devolucionEjemplo.setDespachoId(1);
        devolucionEjemplo.setClienteId(1);
    }

    // @GET

    @Test
    void getDevoluciones_retorna200() throws Exception {
        when(devolucionService.obtenerDevoluciones()).thenReturn(List.of(devolucionEjemplo));

        llamadaFalsa.perform(get("/api/v1/devoluciones"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].motivo").value("Producto dañado"));
    }

    @Test
    void getDevoluciones_retorna204() throws Exception {
        when(devolucionService.obtenerDevoluciones()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/devoluciones"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void getDevolucionById_retorna200() throws Exception {
        when(devolucionService.buscarPorId(1)).thenReturn(devolucionEjemplo);

        llamadaFalsa.perform(get("/api/v1/devoluciones/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.motivo").value("Producto dañado"));
    }

    @Test
    void getDevolucionById_retorna404() throws Exception {
        when(devolucionService.buscarPorId(99))
            .thenThrow(new RuntimeException("Devolucion no encontrada"));

        llamadaFalsa.perform(get("/api/v1/devoluciones/id/99"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void createDevolucion_retorna200() throws Exception {
        when(devolucionService.crearDevolucion(any(Devolucion.class))).thenReturn(devolucionEjemplo);

        llamadaFalsa.perform(post("/api/v1/devoluciones")
                .contentType("application/json")
                .content("""
                    {
                        "fechaDevolucion": "2025-12-01",
                        "motivo": "Producto dañado",
                        "despachoId": 1,
                        "clienteId": 1
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.motivo").value("Producto dañado"));
    }

    @Test
    void createDevolucion_retorna400_cuandoClienteNoExiste() throws Exception {
        when(devolucionService.crearDevolucion(any(Devolucion.class)))
            .thenThrow(new RuntimeException("Cliente no encontrado"));

        llamadaFalsa.perform(post("/api/v1/devoluciones")
                .contentType("application/json")
                .content("""
                    {
                        "fechaDevolucion": "2025-12-01",
                        "motivo": "Producto dañado",
                        "despachoId": 1,
                        "clienteId": 99
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createDevolucion_retorna400_cuandoDespachoNoExiste() throws Exception {
        when(devolucionService.crearDevolucion(any(Devolucion.class)))
            .thenThrow(new RuntimeException("Despacho no encontrado"));

        llamadaFalsa.perform(post("/api/v1/devoluciones")
                .contentType("application/json")
                .content("""
                    {
                        "fechaDevolucion": "2025-12-01",
                        "motivo": "Producto dañado",
                        "despachoId": 99,
                        "clienteId": 1
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // @PUT

    @Test
    void updateDevolucion_retorna200() throws Exception {
        when(devolucionService.actualizarDevolucion(eq(1), any(Devolucion.class))).thenReturn(devolucionEjemplo);

        llamadaFalsa.perform(put("/api/v1/devoluciones/1")
                .contentType("application/json")
                .content("""
                    {
                        "fechaDevolucion": "2025-12-15",
                        "motivo": "Producto incorrecto",
                        "despachoId": 2,
                        "clienteId": 2
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void updateDevolucion_retorna404_cuandoNoExiste() throws Exception {
        when(devolucionService.actualizarDevolucion(eq(99), any(Devolucion.class)))
            .thenThrow(new RuntimeException("Devolucion no encontrada"));

        llamadaFalsa.perform(put("/api/v1/devoluciones/99")
                .contentType("application/json")
                .content("""
                    {
                        "fechaDevolucion": "2025-12-15",
                        "motivo": "Producto incorrecto",
                        "despachoId": 2,
                        "clienteId": 2
                    }
                """))
            .andExpect(status().isNotFound());
    }

    // @DELETE

    @Test
    void deleteDevolucion_retorna204() throws Exception {
        doNothing().when(devolucionService).eliminarDevolucion(1);

        llamadaFalsa.perform(delete("/api/v1/devoluciones/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void deleteDevolucion_retorna404_cuandoNoExiste() throws Exception {
        doThrow(new RuntimeException("Devolucion no existe"))
            .when(devolucionService).eliminarDevolucion(99);

        llamadaFalsa.perform(delete("/api/v1/devoluciones/99"))
            .andExpect(status().isNotFound());
    }
}