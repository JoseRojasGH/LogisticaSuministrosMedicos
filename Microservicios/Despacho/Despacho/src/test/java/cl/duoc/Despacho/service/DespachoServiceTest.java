package cl.duoc.Despacho.service;

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

import cl.duoc.Despacho.client.ClienteClient;
import cl.duoc.Despacho.dto.ClienteDTO;
import cl.duoc.Despacho.model.Despacho;
import cl.duoc.Despacho.repository.DespachoRepository;

@ExtendWith(MockitoExtension.class)
public class DespachoServiceTest {

    @Mock
    private DespachoRepository despachoRepository;

    @Mock
    private ClienteClient clienteClient;

    @InjectMocks
    private DespachoService despachoService;

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
    void listar_noVacia() {
        when(despachoRepository.findAll()).thenReturn(List.of(despachoEjemplo));

        List<Despacho> resultado = despachoService.listar();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombreConductor());
    }

    @Test
    void listar_vacia() {
        when(despachoRepository.findAll()).thenReturn(new ArrayList<>());

        List<Despacho> resultado = despachoService.listar();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarPorId_encontrado() {
        when(despachoRepository.findById(1)).thenReturn(Optional.of(despachoEjemplo));

        Despacho resultado = despachoService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreConductor());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(despachoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            despachoService.buscarPorId(99);
        });

        assertEquals("Despacho no encontrado", error.getMessage());
    }

    // @GET por Cliente

    @Test
    void buscarPorCliente_encontrado() {
        when(despachoRepository.findByClienteId(1)).thenReturn(List.of(despachoEjemplo));

        List<Despacho> resultado = despachoService.buscarPorCliente(1);

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.get(0).getClienteId());
    }

    @Test
    void buscarPorCliente_noEncontrado() {
        when(despachoRepository.findByClienteId(99)).thenReturn(new ArrayList<>());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            despachoService.buscarPorCliente(99);
        });

        assertEquals("No se encontraron despachos para el cliente", error.getMessage());
    }

    // @POST

    @Test
    void guardar_exitoso() {
        ClienteDTO clienteSimulado = new ClienteDTO();
        clienteSimulado.setId(1);

        when(clienteClient.obtenerCliente(1)).thenReturn(clienteSimulado);
        when(despachoRepository.save(any(Despacho.class))).thenReturn(despachoEjemplo);

        Despacho resultado = despachoService.guardar(despachoEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombreConductor());
    }

    @Test
    void guardar_noExitoso() {
        when(clienteClient.obtenerCliente(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            despachoService.guardar(despachoEjemplo);
        });

        assertEquals("Cliente no existe", error.getMessage());
        verify(despachoRepository, never()).save(any(Despacho.class));
    }

    // @PUT

    @Test
    void actualizar_exitoso() {
        Despacho datosActualizados = new Despacho();
        datosActualizados.setNombreConductor("Pedro González");
        datosActualizados.setFechaEntrega(new Date());
        datosActualizados.setClienteId(2);

        when(despachoRepository.findById(1)).thenReturn(Optional.of(despachoEjemplo));
        when(despachoRepository.save(any(Despacho.class))).thenReturn(despachoEjemplo);

        Despacho resultado = despachoService.actualizar(1, datosActualizados);

        assertNotNull(resultado);
        assertEquals("Pedro González", despachoEjemplo.getNombreConductor());
        assertEquals(2, despachoEjemplo.getClienteId());
        verify(despachoRepository, times(1)).save(despachoEjemplo);
    }

    @Test
    void actualizar_noEncontrado() {
        when(despachoRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            despachoService.actualizar(99, new Despacho());
        });

        assertEquals("Despacho no encontrado", error.getMessage());
        verify(despachoRepository, never()).save(any(Despacho.class));
    }

    // @DELETE

    @Test
    void eliminar_exitoso() {
        when(despachoRepository.existsById(1)).thenReturn(true);
        doNothing().when(despachoRepository).deleteById(1);

        despachoService.eliminar(1);

        verify(despachoRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noEncontrado() {
        when(despachoRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            despachoService.eliminar(99);
        });

        assertEquals("Despacho no encontrado", error.getMessage());
        verify(despachoRepository, never()).deleteById(any());
    }
}