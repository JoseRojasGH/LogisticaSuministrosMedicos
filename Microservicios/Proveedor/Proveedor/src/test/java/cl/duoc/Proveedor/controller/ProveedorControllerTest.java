package cl.duoc.Proveedor.controller;

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

import cl.duoc.Proveedor.model.Proveedor;
import cl.duoc.Proveedor.service.ProveedorService;

@WebMvcTest(ProveedorController.class)
public class ProveedorControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private ProveedorService service;

    private Proveedor proveedorEjemplo;

    @BeforeEach
    void setup() {
        proveedorEjemplo = new Proveedor();
        proveedorEjemplo.setId(1);
        proveedorEjemplo.setRut("12345678-9");
        proveedorEjemplo.setRazon_social("Empresa Ejemplo");
        proveedorEjemplo.setCorreo_contacto("contacto@empresa.cl");
        proveedorEjemplo.setUsuarioId(1);
    }

    // @GET

    @Test
    void listar_retorna200() throws Exception {
        when(service.listarProveedores()).thenReturn(List.of(proveedorEjemplo));

        llamadaFalsa.perform(get("/api/v1/proveedores"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].rut").value("12345678-9"));
    }

    @Test
    void listar_retorna204() throws Exception {
        when(service.listarProveedores()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/proveedores"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(proveedorEjemplo);

        llamadaFalsa.perform(get("/api/v1/proveedores/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Proveedor no encontrado"));

        llamadaFalsa.perform(get("/api/v1/proveedores/id/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por RUT

    @Test
    void buscarPorRut_retorna200() throws Exception {
        when(service.buscarPorRut("12345678-9")).thenReturn(proveedorEjemplo);

        llamadaFalsa.perform(get("/api/v1/proveedores/rut/12345678-9"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void buscarPorRut_retorna404() throws Exception {
        when(service.buscarPorRut("00000000-0")).thenThrow(new RuntimeException("Proveedor no encontrado"));

        llamadaFalsa.perform(get("/api/v1/proveedores/rut/00000000-0"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void crear_retorna200() throws Exception {
        when(service.crearProveedor(any(Proveedor.class))).thenReturn(proveedorEjemplo);

        llamadaFalsa.perform(post("/api/v1/proveedores")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "12345678-9",
                        "razon_social": "Empresa Ejemplo",
                        "correo_contacto": "contacto@empresa.cl",
                        "usuarioId": 1
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.rut").value("12345678-9"));
    }

    @Test
    void crear_retorna400() throws Exception {
        when(service.crearProveedor(any(Proveedor.class)))
            .thenThrow(new RuntimeException("Usuario no encontrado"));

        llamadaFalsa.perform(post("/api/v1/proveedores")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "12345678-9",
                        "razon_social": "Empresa Ejemplo",
                        "correo_contacto": "contacto@empresa.cl",
                        "usuarioId": 99
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // @PUT

    @Test
    void actualizar_retorna200() throws Exception {
        when(service.actualizarProveedor(eq(1), any(Proveedor.class))).thenReturn(proveedorEjemplo);

        llamadaFalsa.perform(put("/api/v1/proveedores/1")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "98765432-1",
                        "razon_social": "Nueva Empresa",
                        "correo_contacto": "nuevo@empresa.cl",
                        "usuarioId": 2
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizar_retorna404_cuandoNoExiste() throws Exception {
        when(service.actualizarProveedor(eq(99), any(Proveedor.class)))
            .thenThrow(new RuntimeException("Proveedor no encontrado"));

        llamadaFalsa.perform(put("/api/v1/proveedores/99")
                .contentType("application/json")
                .content("""
                    {
                        "rut": "98765432-1",
                        "razon_social": "Nueva Empresa",
                        "correo_contacto": "nuevo@empresa.cl",
                        "usuarioId": 2
                    }
                """))
            .andExpect(status().isNotFound());
    }
}