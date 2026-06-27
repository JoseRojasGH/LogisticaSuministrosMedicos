package cl.duoc.Pedido.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.Pedido.client.ClienteClient;
import cl.duoc.Pedido.client.DespachoClient;
import cl.duoc.Pedido.client.ProductoClient;
import cl.duoc.Pedido.client.UsuarioClient;
import cl.duoc.Pedido.dto.ClienteDTO;
import cl.duoc.Pedido.dto.DespachoDTO;
import cl.duoc.Pedido.dto.ProductoDTO;
import cl.duoc.Pedido.dto.UsuarioDTO;
import cl.duoc.Pedido.model.Pedido;
import cl.duoc.Pedido.repository.PedidoRepository;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @Mock
    private PedidoRepository repository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private UsuarioClient usuarioClient;

    @Mock
    private ProductoClient productoClient;

    @Mock
    private DespachoClient despachoClient;

    @InjectMocks
    private PedidoService pedidoService;

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
    void listar_noVacia() {
        when(repository.findAll()).thenReturn(List.of(pedidoEjemplo));

        List<Pedido> resultado = pedidoService.listar();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals(3, resultado.get(0).getCantidad());
    }

    @Test
    void listar_vacia() {
        when(repository.findAll()).thenReturn(new ArrayList<>());

        List<Pedido> resultado = pedidoService.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarPorId_encontrado() {
        when(repository.findById(1)).thenReturn(Optional.of(pedidoEjemplo));

        Pedido resultado = pedidoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(3, resultado.getCantidad());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorId(99);
        });

        assertEquals("Pedido no encontrado", error.getMessage());
    }

    // @GET por Cliente

    @Test
    void buscarPorCliente_encontrado() {
        when(repository.findByClienteId(1)).thenReturn(List.of(pedidoEjemplo));

        List<Pedido> resultado = pedidoService.buscarPorCliente(1);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.get(0).getClienteId());
    }

    @Test
    void buscarPorCliente_noEncontrado() {
        when(repository.findByClienteId(99)).thenReturn(new ArrayList<>());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorCliente(99);
        });

        assertEquals("No se encontraron pedidos para el cliente", error.getMessage());
    }

    // @GET por Usuario

    @Test
    void buscarPorUsuario_encontrado() {
        when(repository.findByUsuarioId(1)).thenReturn(List.of(pedidoEjemplo));

        List<Pedido> resultado = pedidoService.buscarPorUsuario(1);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.get(0).getUsuarioId());
    }

    @Test
    void buscarPorUsuario_noEncontrado() {
        when(repository.findByUsuarioId(99)).thenReturn(new ArrayList<>());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorUsuario(99);
        });

        assertEquals("No se encontraron pedidos para el usuario", error.getMessage());
    }

    // @GET por Producto

    @Test
    void buscarPorProducto_encontrado() {
        when(repository.findByProductoId(1)).thenReturn(List.of(pedidoEjemplo));

        List<Pedido> resultado = pedidoService.buscarPorProducto(1);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.get(0).getProductoId());
    }

    @Test
    void buscarPorProducto_noEncontrado() {
        when(repository.findByProductoId(99)).thenReturn(new ArrayList<>());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorProducto(99);
        });

        assertEquals("No se encontraron pedidos para el producto", error.getMessage());
    }

    // @GET por Despacho

    @Test
    void buscarPorDespacho_encontrado() {
        when(repository.findByDespachoId(1)).thenReturn(List.of(pedidoEjemplo));

        List<Pedido> resultado = pedidoService.buscarPorDespacho(1);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.get(0).getDespachoId());
    }

    @Test
    void buscarPorDespacho_noEncontrado() {
        when(repository.findByDespachoId(99)).thenReturn(new ArrayList<>());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorDespacho(99);
        });

        assertEquals("No se encontraron pedidos para el despacho", error.getMessage());
    }

    // @POST

    @Test
    void guardar_exitoso() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        ProductoDTO productoSimulado = new ProductoDTO();
        productoSimulado.setId(1);

        DespachoDTO despachoSimulado = new DespachoDTO();
        despachoSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(productoClient.obtenerProducto(1)).thenReturn(productoSimulado);
        when(despachoClient.obtenerDespacho(1)).thenReturn(despachoSimulado);
        when(repository.save(any(Pedido.class))).thenReturn(pedidoEjemplo);

        Pedido resultado = pedidoService.guardar(pedidoEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(3, resultado.getCantidad());
    }

    @Test
    void guardar_clienteNoExiste() {
        when(clienteClient.obtenerCliente(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.guardar(pedidoEjemplo);
        });

        assertEquals("Cliente no exite", error.getMessage());
        verify(usuarioClient, never()).obtenerUsuario(any());
        verify(productoClient, never()).obtenerProducto(any());
        verify(despachoClient, never()).obtenerDespacho(any());
        verify(repository, never()).save(any(Pedido.class));
    }

    @Test
    void guardar_usuarioNoExiste() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(usuarioClient.obtenerUsuario(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.guardar(pedidoEjemplo);
        });

        assertEquals("Usuario no existe", error.getMessage());
        verify(productoClient, never()).obtenerProducto(any());
        verify(despachoClient, never()).obtenerDespacho(any());
        verify(repository, never()).save(any(Pedido.class));
    }

    @Test
    void guardar_productoNoExiste() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(productoClient.obtenerProducto(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.guardar(pedidoEjemplo);
        });

        assertEquals("Producto no existe", error.getMessage());
        verify(despachoClient, never()).obtenerDespacho(any());
        verify(repository, never()).save(any(Pedido.class));
    }

    @Test
    void guardar_despachoNoExiste() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        ProductoDTO productoSimulado = new ProductoDTO();
        productoSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(productoClient.obtenerProducto(1)).thenReturn(productoSimulado);
        when(despachoClient.obtenerDespacho(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.guardar(pedidoEjemplo);
        });

        assertEquals("Despacho no existe", error.getMessage());
        verify(repository, never()).save(any(Pedido.class));
    }

    // @PUT

    @Test
    void actualizar_exitoso() {
        Pedido datosActualizados = new Pedido();
        datosActualizados.setCantidad(5);
        datosActualizados.setPrecioTotal(7500);
        datosActualizados.setProductoId(2);
        datosActualizados.setDespachoId(2);
        datosActualizados.setClienteId(2);
        datosActualizados.setUsuarioId(2);

        when(repository.findById(1)).thenReturn(Optional.of(pedidoEjemplo));
        when(repository.save(any(Pedido.class))).thenReturn(pedidoEjemplo);

        Pedido resultado = pedidoService.actualizar(1, datosActualizados);

        assertNotNull(resultado);
        assertEquals(5, pedidoEjemplo.getCantidad());
        assertEquals(7500, pedidoEjemplo.getPrecioTotal());
        assertEquals(2, pedidoEjemplo.getClienteId());
        verify(repository, times(1)).save(pedidoEjemplo);
    }

    @Test
    void actualizar_noEncontrado() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pedidoService.actualizar(99, new Pedido());
        });

        assertEquals("Pedido no encontrado", error.getMessage());
        verify(repository, never()).save(any(Pedido.class));
    }

    // @DELETE

    @Test
    void eliminar_exitoso() {
        doNothing().when(repository).deleteById(1);

        pedidoService.eliminar(1);

        verify(repository, times(1)).deleteById(1);
    }
}
