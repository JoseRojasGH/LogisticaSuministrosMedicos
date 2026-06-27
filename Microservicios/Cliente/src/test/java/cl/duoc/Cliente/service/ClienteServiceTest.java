package cl.duoc.Cliente.service;

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

import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setup() {
        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setRut("12345678-9");
        clienteEjemplo.setNombre("Roberto Salinas");
        clienteEjemplo.setDireccion("Avenida Siempre Viva 123");
        clienteEjemplo.setCorreo("roberto@gmail.com");
    }

    // @GET

    @Test
    void listarClientes_noVacia() {
        when(clienteRepository.findAll()).thenReturn(List.of(clienteEjemplo));

        List<Cliente> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Roberto Salinas", resultado.get(0).getNombre());
    }

    @Test
    void listarClientes_vacia() {
        when(clienteRepository.findAll()).thenReturn(new ArrayList<>());

        List<Cliente> resultado = clienteService.listarClientes();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarClientePorId_encontrado() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteEjemplo));

        Cliente resultado = clienteService.buscarClientePorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Roberto Salinas", resultado.getNombre());
    }

    @Test
    void buscarClientePorId_noEncontrado() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarClientePorId(99);
        });

        assertEquals("Cliente no encontrado", error.getMessage());
    }

    // @GET por RUT

    @Test
    void buscarClientePorRut_encontrado() {
        when(clienteRepository.findByRut("12345678-9")).thenReturn(Optional.of(clienteEjemplo));

        Cliente resultado = clienteService.buscarClientePorRut("12345678-9");

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void buscarClientePorRut_noEncontrado() {
        when(clienteRepository.findByRut("00000000-0")).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarClientePorRut("00000000-0");
        });

        assertEquals("Cliente no encontrado", error.getMessage());
    }

    // @POST

    @Test
    void crearCliente_exitoso() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEjemplo);

        Cliente resultado = clienteService.crearCliente(clienteEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("12345678-9", resultado.getRut());
        assertEquals("Roberto Salinas", resultado.getNombre());
    }

    // @PUT

    @Test
    void actualizarCliente_exitoso() {
        Cliente datosActualizados = new Cliente();
        datosActualizados.setRut("98765432-1");
        datosActualizados.setNombre("Ana González");
        datosActualizados.setDireccion("Calle Nueva 456");
        datosActualizados.setCorreo("ana@gmail.com");

        when(clienteRepository.findById(1)).thenReturn(Optional.of(clienteEjemplo));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteEjemplo);

        Cliente resultado = clienteService.actualizarCliente(1, datosActualizados);

        assertNotNull(resultado);
        assertEquals("98765432-1", clienteEjemplo.getRut());
        assertEquals("Ana González", clienteEjemplo.getNombre());
        assertEquals("Calle Nueva 456", clienteEjemplo.getDireccion());
        assertEquals("ana@gmail.com", clienteEjemplo.getCorreo());
        verify(clienteRepository, times(1)).save(clienteEjemplo);
    }

    @Test
    void actualizarCliente_noEncontrado() {
        when(clienteRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            clienteService.actualizarCliente(99, new Cliente());
        });

        assertEquals("Cliente no encontrado", error.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}
