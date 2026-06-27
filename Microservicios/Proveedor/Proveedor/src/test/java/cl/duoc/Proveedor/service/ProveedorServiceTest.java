package cl.duoc.Proveedor.service;

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

import cl.duoc.Proveedor.client.UsuarioClient;
import cl.duoc.Proveedor.dto.UsuarioDTO;
import cl.duoc.Proveedor.model.Proveedor;
import cl.duoc.Proveedor.repository.ProveedorRepository;

@ExtendWith(MockitoExtension.class)
public class ProveedorServiceTest {

    @Mock
    private ProveedorRepository proveedorRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private ProveedorService proveedorService;

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
    void listarProveedores_noVacia() {
        when(proveedorRepository.findAll()).thenReturn(List.of(proveedorEjemplo));

        List<Proveedor> resultado = proveedorService.listarProveedores();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("12345678-9", resultado.get(0).getRut());
    }

    @Test
    void listarProveedores_vacia() {
        when(proveedorRepository.findAll()).thenReturn(new ArrayList<>());

        List<Proveedor> resultado = proveedorService.listarProveedores();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarPorId_encontrado() {
        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedorEjemplo));

        Proveedor resultado = proveedorService.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void buscarPorId_noEncontrado() {
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            proveedorService.buscarPorId(99);
        });

        assertEquals("Proveedor no encontrado", error.getMessage());
    }

    // @GET por RUT

    @Test
    void buscarPorRut_encontrado() {
        when(proveedorRepository.findByRut("12345678-9")).thenReturn(Optional.of(proveedorEjemplo));

        Proveedor resultado = proveedorService.buscarPorRut("12345678-9");

        assertNotNull(resultado);
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void buscarPorRut_noEncontrado() {
        when(proveedorRepository.findByRut("00000000-0")).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            proveedorService.buscarPorRut("00000000-0");
        });

        assertEquals("Proveedor no encontrado", error.getMessage());
    }

    // @POST

    @Test
    void crearProveedor_exitoso() {
        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorEjemplo);

        Proveedor resultado = proveedorService.crearProveedor(proveedorEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("12345678-9", resultado.getRut());
    }

    @Test
    void crearProveedor_noExitoso() {
        when(usuarioClient.obtenerUsuario(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            proveedorService.crearProveedor(proveedorEjemplo);
        });

        assertEquals("Usuario no encontrado", error.getMessage());
        verify(proveedorRepository, never()).save(any(Proveedor.class));
    }

    // @PUT

    @Test
    void actualizarProveedor_exitoso() {
        Proveedor datosActualizados = new Proveedor();
        datosActualizados.setRut("98765432-1");
        datosActualizados.setRazon_social("Nueva Empresa");
        datosActualizados.setCorreo_contacto("nuevo@empresa.cl");
        datosActualizados.setUsuarioId(2);

        when(proveedorRepository.findById(1)).thenReturn(Optional.of(proveedorEjemplo));
        when(proveedorRepository.save(any(Proveedor.class))).thenReturn(proveedorEjemplo);

        Proveedor resultado = proveedorService.actualizarProveedor(1, datosActualizados);

        assertNotNull(resultado);
        assertEquals("98765432-1", proveedorEjemplo.getRut());
        assertEquals("Nueva Empresa", proveedorEjemplo.getRazon_social());
        assertEquals("nuevo@empresa.cl", proveedorEjemplo.getCorreo_contacto());
        verify(proveedorRepository, times(1)).save(proveedorEjemplo);
    }

    @Test
    void actualizarProveedor_noEncontrado() {
        when(proveedorRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            proveedorService.actualizarProveedor(99, new Proveedor());
        });

        assertEquals("Proveedor no encontrado", error.getMessage());
        verify(proveedorRepository, never()).save(any(Proveedor.class));
    }
}