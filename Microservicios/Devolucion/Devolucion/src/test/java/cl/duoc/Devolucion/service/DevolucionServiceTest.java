package cl.duoc.Devolucion.service;

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

import cl.duoc.Devolucion.client.ClienteClient;
import cl.duoc.Devolucion.client.DespachoClient;
import cl.duoc.Devolucion.dto.ClienteDTO;
import cl.duoc.Devolucion.dto.DespachoDTO;
import cl.duoc.Devolucion.model.Devolucion;
import cl.duoc.Devolucion.repository.DevolucionRepository;

@ExtendWith(MockitoExtension.class)
public class DevolucionServiceTest {

    @Mock
    private DevolucionRepository devolucionRepository;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private DespachoClient despachoClient;

    @InjectMocks
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
    void obtenerDevoluciones_noVacia() {
        when(devolucionRepository.findAll()).thenReturn(List.of(devolucionEjemplo));

        List<Devolucion> resultado = devolucionService.obtenerDevoluciones();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Producto dañado", resultado.get(0).getMotivo());
    }

    @Test
    void obtenerDevoluciones_vacia() {
        when(devolucionRepository.findAll()).thenReturn(new ArrayList<>());

        List<Devolucion> resultado = devolucionService.obtenerDevoluciones();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarPorId_encontrado() {
        when(devolucionRepository.findById(1)).thenReturn(Optional.of(devolucionEjemplo));

        Devolucion resultado = devolucionService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Producto dañado", resultado.getMotivo());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(devolucionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            devolucionService.buscarPorId(99);
        });

        assertEquals("Devolucion no encontrada", error.getMessage());
    }

    // @POST

    @Test
    void crearDevolucion_exitoso() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        DespachoDTO despachoSimulado = new DespachoDTO();
        despachoSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(despachoClient.obtenerDespacho(1)).thenReturn(despachoSimulado);
        when(devolucionRepository.save(any(Devolucion.class))).thenReturn(devolucionEjemplo);

        Devolucion resultado = devolucionService.crearDevolucion(devolucionEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Producto dañado", resultado.getMotivo());
    }

    @Test
    void crearDevolucion_clienteNoEncontrado() {
        when(clienteClient.obtenerCliente(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            devolucionService.crearDevolucion(devolucionEjemplo);
        });

        assertEquals("Cliente no encontrado", error.getMessage());
        verify(despachoClient, never()).obtenerDespacho(any());
        verify(devolucionRepository, never()).save(any(Devolucion.class));
    }

    @Test
    void crearDevolucion_despachoNoEncontrado() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(despachoClient.obtenerDespacho(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            devolucionService.crearDevolucion(devolucionEjemplo);
        });

        assertEquals("Despacho no encontrado", error.getMessage());
        verify(devolucionRepository, never()).save(any(Devolucion.class));
    }

    // @PUT

    @Test
    void actualizarDevolucion_exitoso() {
        Devolucion datosActualizados = new Devolucion();
        datosActualizados.setFechaDevolucion(new Date());
        datosActualizados.setMotivo("Producto incorrecto");
        datosActualizados.setDespachoId(2);
        datosActualizados.setClienteId(2);

        when(devolucionRepository.findById(1)).thenReturn(Optional.of(devolucionEjemplo));
        when(devolucionRepository.save(any(Devolucion.class))).thenReturn(devolucionEjemplo);

        Devolucion resultado = devolucionService.actualizarDevolucion(1, datosActualizados);

        assertNotNull(resultado);
        assertEquals("Producto incorrecto", devolucionEjemplo.getMotivo());
        assertEquals(2, devolucionEjemplo.getDespachoId());
        assertEquals(2, devolucionEjemplo.getClienteId());
        verify(devolucionRepository, times(1)).save(devolucionEjemplo);
    }

    @Test
    void actualizarDevolucion_noEncontrado() {
        when(devolucionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            devolucionService.actualizarDevolucion(99, new Devolucion());
        });

        assertEquals("Devolucion no encontrada", error.getMessage());
        verify(devolucionRepository, never()).save(any(Devolucion.class));
    }

    // @DELETE

    @Test
    void eliminarDevolucion_exitoso() {
        when(devolucionRepository.existsById(1)).thenReturn(true);
        when(devolucionRepository.findById(1)).thenReturn(Optional.of(devolucionEjemplo));
        doNothing().when(devolucionRepository).delete(devolucionEjemplo);

        devolucionService.eliminarDevolucion(1);

        verify(devolucionRepository, times(1)).delete(devolucionEjemplo);
    }

    @Test
    void eliminarDevolucion_noEncontrado() {
        when(devolucionRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            devolucionService.eliminarDevolucion(99);
        });

        assertEquals("Devolucion no existe", error.getMessage());
        verify(devolucionRepository, never()).delete(any(Devolucion.class));
    }
}