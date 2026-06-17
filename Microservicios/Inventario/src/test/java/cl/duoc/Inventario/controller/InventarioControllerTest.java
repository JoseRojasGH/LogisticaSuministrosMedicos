package cl.duoc.Inventario.controller;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


    @Test
    void buscarPorId_retorna200() throws Exception{
        when(service.buscarPorId(1)).thenReturn(inventarioEjemplo);

        llamadaFalsa.perform(get("/api/v1/inventarios/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1));

    }
}
