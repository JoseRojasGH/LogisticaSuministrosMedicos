package cl.duoc.Inventario.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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

import cl.duoc.Inventario.model.Estado;
import cl.duoc.Inventario.model.Inventario;
import cl.duoc.Inventario.service.InventarioService;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {
    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private InventarioService service;

    private Inventario inventarioEjemplo;

    @BeforeEach
    void SetUp(){
        inventarioEjemplo = new Inventario();
        inventarioEjemplo.setId(1);
        inventarioEjemplo.setStock_actual(15);
        inventarioEjemplo.setUsuarioId(1);
        inventarioEjemplo.setEstado(new Estado(1, "disponible"));
    }

    // @GET

    @Test
    void listarInventarios_retorna200() throws Exception {
        List<Inventario> lista = List.of(inventarioEjemplo);
        when(service.listarInventarios()).thenReturn(lista);

        llamadaFalsa.perform(get("/api/v1/inventarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].stock_actual").value(15));
    }

    @Test
    void listarInventarios_retorna204() throws Exception {
        when(service.listarInventarios()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/inventarios"))
            .andExpect(status().isNoContent()); 
    }


    // @GET por ID

    @Test
    void buscarPorId_retorna200() throws Exception{
        when(service.buscarPorId(1)).thenReturn(inventarioEjemplo);

        llamadaFalsa.perform(get("/api/v1/inventarios/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));

    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(service.buscarPorId(1)).thenThrow(new RuntimeException("Inventario no encontrado"));

        llamadaFalsa.perform(get("/api/v1/inventarios/1"))
            .andExpect(status().isNotFound());
    }

    //@POST

    @Test
    void crearInventario_retorna200() throws Exception {
        when(service.crearInventario(any(Inventario.class))).thenReturn(inventarioEjemplo);

        llamadaFalsa.perform(post("/api/v1/inventarios")
                .contentType("application/json")
                .content("""
                    {
                        "id": 1,
                        "stock_actual": 15,
                        "usuarioId": 1,
                        "estado": { "id": 1, "disponibilidad": "disponible" }
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.stock_actual").value(15));
    }

    @Test
    void crearInventario_retorna500() throws Exception {
        when(service.crearInventario(any(Inventario.class)))
            .thenThrow(new RuntimeException("Usuario no existe"));

        llamadaFalsa.perform(post("/api/v1/inventarios")
                .contentType("application/json")
                .content("""
                    {
                        "id": 1,
                        "stock_actual": 15,
                        "usuarioId": 99,
                        "estado": { "id": 1, "disponibilidad": "disponible" }
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    //@PATCH

    @Test
    void actualizarStock_retorna200_cuandoEsExitoso() throws Exception {
        doNothing().when(service).actualizarStockPorId(1, 50);

        llamadaFalsa.perform(patch("/api/v1/inventarios/1/50"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarStock_retorna404_cuandoFalla() throws Exception {
        doThrow(new RuntimeException("Error al actualizar")).when(service).actualizarStockPorId(1, 50);

        llamadaFalsa.perform(patch("/api/v1/inventarios/1/50"))
            .andExpect(status().isNotFound());
    }

}
