package cl.duoc.Usuario.service;

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

import cl.duoc.Usuario.model.Rol;
import cl.duoc.Usuario.model.Usuario;
import cl.duoc.Usuario.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setup() {
        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1);
        usuarioEjemplo.setNombre("Roberto Salinas");
        usuarioEjemplo.setContraseña("pass123");
        usuarioEjemplo.setCorreo("roberto@gmail.com");
        usuarioEjemplo.setRol(new Rol(1, "Admin"));
    }

    // @GET

    @Test
    void listarUsuarios_noVacia() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioEjemplo));

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Roberto Salinas", resultado.get(0).getNombre());
    }

    @Test
    void listarUsuarios_vacia() {
        when(usuarioRepository.findAll()).thenReturn(new ArrayList<>());

        List<Usuario> resultado = usuarioService.listarUsuarios();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    // @GET por ID

    @Test
    void buscarporId_encontrado() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.buscarporId(1);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Roberto Salinas", resultado.getNombre());
    }

    @Test
    void buscarporId_noEncontrado() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarporId(99);
        });

        assertEquals("Usuario no encontrado", error.getMessage());
    }

    // @GET por Correo

    @Test
    void buscarporCorreo_encontrado() {
        when(usuarioRepository.findByCorreo("roberto@gmail.com")).thenReturn(Optional.of(usuarioEjemplo));

        Usuario resultado = usuarioService.buscarporCorreo("roberto@gmail.com");

        assertNotNull(resultado);
        assertEquals("roberto@gmail.com", resultado.getCorreo());
    }

    @Test
    void buscarporCorreo_noEncontrado() {
        when(usuarioRepository.findByCorreo("noexiste@gmail.com")).thenReturn(Optional.empty());

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarporCorreo("noexiste@gmail.com");
        });

        assertEquals("Usuario no encontrado", error.getMessage());
    }

    // @POST

    @Test
    void crearUsuario_exitoso() {
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEjemplo);

        Usuario resultado = usuarioService.crearUsuario(usuarioEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Roberto Salinas", resultado.getNombre());
    }

    // @DELETE

    @Test
    void eliminarUsuario_exitoso() {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));
        doNothing().when(usuarioRepository).delete(usuarioEjemplo);

        usuarioService.eliminarUsuario(1);

        verify(usuarioRepository, times(1)).delete(usuarioEjemplo);
    }

    @Test
    void eliminarUsuario_noEncontrado() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.eliminarUsuario(99);
        });

        assertEquals("Usuario no existe", error.getMessage());
        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }

    // @PATCH

    @Test
    void actualizarContraseñaUsuario_exitoso() {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioEjemplo);

        usuarioService.actualizarContraseñaUsuario(1, "nuevaPass456");

        assertEquals("nuevaPass456", usuarioEjemplo.getContraseña());
        verify(usuarioRepository, times(1)).save(usuarioEjemplo);
    }

    @Test
    void actualizarContraseñaUsuario_noEncontrado() {
        when(usuarioRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.actualizarContraseñaUsuario(99, "nuevaPass456");
        });

        assertEquals("Usuario no existe", error.getMessage());
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }
}
