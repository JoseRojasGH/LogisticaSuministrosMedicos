package cl.duoc.Producto.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.Producto.client.InventarioClient;
import cl.duoc.Producto.client.ProveedorClient;
import cl.duoc.Producto.client.UsuarioClient;
import cl.duoc.Producto.dto.InventarioDTO;
import cl.duoc.Producto.dto.ProveedorDTO;
import cl.duoc.Producto.dto.UsuarioDTO;
import cl.duoc.Producto.model.Categoria;
import cl.duoc.Producto.model.Producto;
import cl.duoc.Producto.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProveedorClient proveedorClient;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
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
    void listarProductos_noVacia() {
        when(productoRepository.findAll()).thenReturn(List.of(productoEjemplo));

        List<Producto> resultado = productoService.listarProductos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Producto Test", resultado.get(0).getNombre());
    }

    @Test
    void listarProductos_vacia() {
        when(productoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Producto> resultado = productoService.listarProductos();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarPorId_encontrado() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));

        Producto resultado = productoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Producto Test", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.buscarPorId(99);
        });

        assertEquals("Producto no encontrado", error.getMessage());
    }

    // @GET por Nombre

    @Test
    void buscarPorNombre_encontrado() {
        when(productoRepository.findByNombre("Producto Test")).thenReturn(List.of(productoEjemplo));

        List<Producto> resultado = productoService.buscarPorNombre("Producto Test");

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals("Producto Test", resultado.get(0).getNombre());
    }

    @Test
    void buscarPorNombre_noEncontrado() {
        when(productoRepository.findByNombre("No existe")).thenReturn(new ArrayList<>());

        List<Producto> resultado = productoService.buscarPorNombre("No existe");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET precio por ID

    @Test
    void buscarPrecioPorId_encontrado() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));

        Double precio = productoService.buscarPrecioPorId(1);

        assertNotNull(precio);
        assertEquals(1500.0, precio);
    }

    @Test
    void buscarPrecioPorId_noEncontrado() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.buscarPrecioPorId(99);
        });

        assertEquals("Producto no encontrado", error.getMessage());
    }

    // @GET fecha vencimiento por ID

    @Test
    void buscarFechaVencimientoPorId_encontrado() {
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));

        Date fecha = productoService.buscarFechaVencimientoPorId(1);

        assertNotNull(fecha);
        assertEquals(productoEjemplo.getFecha_vencimiento(), fecha);
    }

    @Test
    void buscarFechaVencimientoPorId_noEncontrado() {
        when(productoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.buscarFechaVencimientoPorId(99);
        });

        assertEquals("Producto no encontrado", error.getMessage());
    }

    // @POST

    @Test
    void crearProducto_exitoso() {
        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        InventarioDTO inventarioSimulado = new InventarioDTO();
        inventarioSimulado.setId(1);

        ProveedorDTO proveedorSimulado = new ProveedorDTO();
        proveedorSimulado.setId(1);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(inventarioClient.obtenerInventario(1)).thenReturn(inventarioSimulado);
        when(proveedorClient.obtenerProveedor(1)).thenReturn(proveedorSimulado);
        when(productoRepository.save(any(Producto.class))).thenReturn(productoEjemplo);

        Producto resultado = productoService.crearProducto(productoEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Producto Test", resultado.getNombre());
    }

    @Test
    void crearProducto_usuarioNoExiste() {
        when(usuarioClient.obtenerUsuario(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(productoEjemplo);
        });

        assertEquals("Usuario no existe", error.getMessage());
        verify(inventarioClient, never()).obtenerInventario(any());
        verify(proveedorClient, never()).obtenerProveedor(any());
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void crearProducto_inventarioNoExiste() {
        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(inventarioClient.obtenerInventario(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(productoEjemplo);
        });

        assertEquals("Inventario no existe", error.getMessage());
        verify(proveedorClient, never()).obtenerProveedor(any());
        verify(productoRepository, never()).save(any(Producto.class));
    }

    @Test
    void crearProducto_proveedorNoExiste() {
        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        InventarioDTO inventarioSimulado = new InventarioDTO();
        inventarioSimulado.setId(1);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(inventarioClient.obtenerInventario(1)).thenReturn(inventarioSimulado);
        when(proveedorClient.obtenerProveedor(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(productoEjemplo);
        });

        assertEquals("Proveedor no existe", error.getMessage());
        verify(productoRepository, never()).save(any(Producto.class));
    }

    // @PATCH precio

    @Test
    void actualizarPrecioProducto_exitoso() {
        when(productoRepository.existsById(1)).thenReturn(true);
        when(productoRepository.findById(1)).thenReturn(Optional.of(productoEjemplo));
        when(productoRepository.save(any(Producto.class))).thenReturn(productoEjemplo);

        productoService.actualizarPrecioProducto(1, 2000.0);

        assertEquals(2000.0, productoEjemplo.getPrecio());
        verify(productoRepository, times(1)).save(productoEjemplo);
    }

    @Test
    void actualizarPrecioProducto_noEncontrado() {
        when(productoRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.actualizarPrecioProducto(99, 2000.0);
        });

        assertEquals("Producto no encontrado", error.getMessage());
        verify(productoRepository, never()).save(any(Producto.class));
    }

    // @DELETE

    @Test
    void eliminarProducto_exitoso() {
        when(productoRepository.existsById(1)).thenReturn(true);
        doNothing().when(productoRepository).deleteById(1);

        productoService.eliminarProducto(1);

        verify(productoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminarProducto_noEncontrado() {
        when(productoRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            productoService.eliminarProducto(99);
        });

        assertEquals("Producto no encontrado", error.getMessage());
        verify(productoRepository, never()).deleteById(any());
    }
}