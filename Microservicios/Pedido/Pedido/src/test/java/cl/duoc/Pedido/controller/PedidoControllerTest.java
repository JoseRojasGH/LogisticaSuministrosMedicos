package cl.duoc.Pedido.controller;

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

import cl.duoc.Pedido.model.Pedido;
import cl.duoc.Pedido.service.PedidoService;

@WebMvcTest(PedidoController.class)
public class PedidoControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private PedidoService service;

    private Pedido pedidoEjemplo;

    @BeforeEach
    void setup() {
        pedidoEjemplo = new Pedido();
        pedidoEjemplo.setId(1);
        pedidoEjemplo.setCantidad(3);
        pedidoEjemplo.setPrecioTotal(4500);
        pedidoEjemplo.setProductoId(1);
        pedidoEjemplo.setDespachoId(1);
        pedidoEjemplo.setClienteId(1);
        pedidoEjemplo.setUsuarioId(1);
    }

    // @GET

    @Test
    void listar_retorna200() throws Exception {
        when(service.listar()).thenReturn(List.of(pedidoEjemplo));

        llamadaFalsa.perform(get("/api/v1/pedidos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].cantidad").value(3));
    }

    @Test
    void listar_retorna204() throws Exception {
        when(service.listar()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/pedidos"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void buscar_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(pedidoEjemplo);

        llamadaFalsa.perform(get("/api/v1/pedidos/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.cantidad").value(3));
    }

    @Test
    void buscar_retorna404() throws Exception {
        when(service.buscarPorId(99))
            .thenThrow(new RuntimeException("Pedido no encontrado"));

        llamadaFalsa.perform(get("/api/v1/pedidos/id/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Cliente

    @Test
    void buscarPorCliente_retorna200() throws Exception {
        when(service.buscarPorCliente(1)).thenReturn(List.of(pedidoEjemplo));

        llamadaFalsa.perform(get("/api/v1/pedidos/cliente/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].clienteId").value(1));
    }

    @Test
    void buscarPorCliente_retorna404() throws Exception {
        when(service.buscarPorCliente(99))
            .thenThrow(new RuntimeException("No se encontraron pedidos para el cliente"));

        llamadaFalsa.perform(get("/api/v1/pedidos/cliente/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Usuario

    @Test
    void buscarPorUsuario_retorna200() throws Exception {
        when(service.buscarPorUsuario(1)).thenReturn(List.of(pedidoEjemplo));

        llamadaFalsa.perform(get("/api/v1/pedidos/usuario/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].usuarioId").value(1));
    }

    @Test
    void buscarPorUsuario_retorna404() throws Exception {
        when(service.buscarPorUsuario(99))
            .thenThrow(new RuntimeException("No se encontraron pedidos para el usuario"));

        llamadaFalsa.perform(get("/api/v1/pedidos/usuario/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Producto

    @Test
    void buscarPorProducto_retorna200() throws Exception {
        when(service.buscarPorProducto(1)).thenReturn(List.of(pedidoEjemplo));

        llamadaFalsa.perform(get("/api/v1/pedidos/producto/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].productoId").value(1));
    }

    @Test
    void buscarPorProducto_retorna404() throws Exception {
        when(service.buscarPorProducto(99))
            .thenThrow(new RuntimeException("No se encontraron pedidos para el producto"));

        llamadaFalsa.perform(get("/api/v1/pedidos/producto/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Despacho

    @Test
    void buscarPorDespacho_retorna200() throws Exception {
        when(service.buscarPorDespacho(1)).thenReturn(List.of(pedidoEjemplo));

        llamadaFalsa.perform(get("/api/v1/pedidos/despacho/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].despachoId").value(1));
    }

    @Test
    void buscarPorDespacho_retorna404() throws Exception {
        when(service.buscarPorDespacho(99))
            .thenThrow(new RuntimeException("No se encontraron pedidos para el despacho"));

        llamadaFalsa.perform(get("/api/v1/pedidos/despacho/99"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void guardar_retorna200() throws Exception {
        when(service.guardar(any(Pedido.class))).thenReturn(pedidoEjemplo);

        llamadaFalsa.perform(post("/api/v1/pedidos")
                .contentType("application/json")
                .content("""
                    {
                        "cantidad": 3,
                        "precioTotal": 4500,
                        "productoId": 1,
                        "despachoId": 1,
                        "clienteId": 1,
                        "usuarioId": 1
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.cantidad").value(3));
    }

    @Test
    void guardar_retorna400() throws Exception {
        when(service.guardar(any(Pedido.class)))
            .thenThrow(new RuntimeException("Cliente no existe"));

        llamadaFalsa.perform(post("/api/v1/pedidos")
                .contentType("application/json")
                .content("""
                    {
                        "cantidad": 3,
                        "precioTotal": 4500,
                        "productoId": 1,
                        "despachoId": 1,
                        "clienteId": 99,
                        "usuarioId": 1
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // @DELETE

    @Test
    void eliminar_retorna204() throws Exception {
        doNothing().when(service).eliminar(1);

        llamadaFalsa.perform(delete("/api/v1/pedidos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_retorna404_cuandoFalla() throws Exception {
        doThrow(new RuntimeException("Pedido no encontrado"))
            .when(service).eliminar(99);

        llamadaFalsa.perform(delete("/api/v1/pedidos/99"))
            .andExpect(status().isNotFound());
    }
}