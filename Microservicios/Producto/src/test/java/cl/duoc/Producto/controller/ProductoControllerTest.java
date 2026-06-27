package cl.duoc.Producto.controller;

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

import cl.duoc.Producto.model.Categoria;
import cl.duoc.Producto.model.Producto;
import cl.duoc.Producto.service.ProductoService;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private ProductoService productoService;

    private Producto productoEjemplo;

    @BeforeEach
    void setup() {
        productoEjemplo = new Producto();
        productoEjemplo.setId(1);
        productoEjemplo.setNombre("Producto Test");
        productoEjemplo.setNumero_lote("LOTE-001");
        productoEjemplo.setPrecio(1500.0);
        productoEjemplo.setFecha_vencimiento(new Date());
        productoEjemplo.setUsuarioId(1);
        productoEjemplo.setInventarioId(1);
        productoEjemplo.setProveedorId(1);
        productoEjemplo.setCategoria(new Categoria(1, "Electrónico"));
    }

    // @GET

    @Test
    void listarProductos_retorna200() throws Exception {
        when(productoService.listarProductos()).thenReturn(List.of(productoEjemplo));

        llamadaFalsa.perform(get("/api/v1/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Producto Test"));
    }

    @Test
    void listarProductos_retorna204() throws Exception {
        when(productoService.listarProductos()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/productos"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void buscarPorId_retorna200() throws Exception {
        when(productoService.buscarPorId(1)).thenReturn(productoEjemplo);

        llamadaFalsa.perform(get("/api/v1/productos/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Producto Test"));
    }

    @Test
    void buscarPorId_retorna404() throws Exception {
        when(productoService.buscarPorId(99))
            .thenThrow(new RuntimeException("Producto no encontrado"));

        llamadaFalsa.perform(get("/api/v1/productos/id/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Nombre

    @Test
    void buscarPorNombre_retorna200() throws Exception {
        when(productoService.buscarPorNombre("Producto Test")).thenReturn(List.of(productoEjemplo));

        llamadaFalsa.perform(get("/api/v1/productos/nombre/Producto Test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Producto Test"));
    }

    @Test
    void buscarPorNombre_retorna204() throws Exception {
        when(productoService.buscarPorNombre("No existe")).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/productos/nombre/No existe"))
            .andExpect(status().isNoContent());
    }

    // @GET precio por ID

    @Test
    void buscarPrecioPorId_retorna200() throws Exception {
        when(productoService.buscarPrecioPorId(1)).thenReturn(1500.0);

        llamadaFalsa.perform(get("/api/v1/productos/precio/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(1500.0));
    }

    @Test
    void buscarPrecioPorId_retorna404() throws Exception {
        when(productoService.buscarPrecioPorId(99))
            .thenThrow(new RuntimeException("Producto no encontrado"));

        llamadaFalsa.perform(get("/api/v1/productos/precio/99"))
            .andExpect(status().isNotFound());
    }

    // @GET fecha vencimiento por ID

    @Test
    void buscarFechaVencimientoPorId_retorna200() throws Exception {
        when(productoService.buscarFechaVencimientoPorId(1))
            .thenReturn(productoEjemplo.getFecha_vencimiento());

        llamadaFalsa.perform(get("/api/v1/productos/fecha/1"))
            .andExpect(status().isOk());
    }

    @Test
    void buscarFechaVencimientoPorId_retorna404() throws Exception {
        when(productoService.buscarFechaVencimientoPorId(99))
            .thenThrow(new RuntimeException("Producto no encontrado"));

        llamadaFalsa.perform(get("/api/v1/productos/fecha/99"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void crearProducto_retorna200() throws Exception {
        when(productoService.crearProducto(any(Producto.class))).thenReturn(productoEjemplo);

        llamadaFalsa.perform(post("/api/v1/productos")
                .contentType("application/json")
                .content("""
                    {
                        "nombre": "Producto Test",
                        "numero_lote": "LOTE-001",
                        "precio": 1500.0,
                        "fecha_vencimiento": "2025-12-01",
                        "usuarioId": 1,
                        "inventarioId": 1,
                        "proveedorId": 1,
                        "categoria": { "id": 1, "tipo_producto": "Electrónico" }
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Producto Test"));
    }

    @Test
    void crearProducto_retorna400_cuandoFalla() throws Exception {
        when(productoService.crearProducto(any(Producto.class)))
            .thenThrow(new RuntimeException("Usuario no existe"));

        llamadaFalsa.perform(post("/api/v1/productos")
                .contentType("application/json")
                .content("""
                    {
                        "nombre": "Producto Test",
                        "numero_lote": "LOTE-001",
                        "precio": 1500.0,
                        "fecha_vencimiento": "2025-12-01",
                        "usuarioId": 99,
                        "inventarioId": 1,
                        "proveedorId": 1,
                        "categoria": { "id": 1, "tipo_producto": "Electrónico" }
                    }
                """))
            .andExpect(status().isBadRequest());
    }

    // @PATCH precio

    @Test
    void actualizarPrecioProducto_retorna200() throws Exception {
        doNothing().when(productoService).actualizarPrecioProducto(1, 2000.0);

        llamadaFalsa.perform(patch("/api/v1/productos/1/2000.0"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarPrecioProducto_retorna404_cuandoNoExiste() throws Exception {
        doThrow(new RuntimeException("Producto no encontrado"))
            .when(productoService).actualizarPrecioProducto(99, 2000.0);

        llamadaFalsa.perform(patch("/api/v1/productos/99/2000.0"))
            .andExpect(status().isNotFound());
    }

    // @DELETE

    @Test
    void eliminarProducto_retorna204() throws Exception {
        doNothing().when(productoService).eliminarProducto(1);

        llamadaFalsa.perform(delete("/api/v1/productos/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarProducto_retorna404_cuandoNoExiste() throws Exception {
        doThrow(new RuntimeException("Producto no encontrado"))
            .when(productoService).eliminarProducto(99);

        llamadaFalsa.perform(delete("/api/v1/productos/99"))
            .andExpect(status().isNotFound());
    }
}