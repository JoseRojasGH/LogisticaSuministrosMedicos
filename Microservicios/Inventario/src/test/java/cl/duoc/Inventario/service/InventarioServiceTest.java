package cl.duoc.Inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.Inventario.dto.UsuarioDTO;
import cl.duoc.Inventario.client.UsuarioClient;
import cl.duoc.Inventario.model.Estado;
import cl.duoc.Inventario.model.Inventario;
import cl.duoc.Inventario.repository.InventarioRepository;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private UsuarioClient usuarioClient;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventarioEjemplo;

    @BeforeEach
    void setup(){

        inventarioEjemplo = new Inventario();
        inventarioEjemplo.setId(1);
        inventarioEjemplo.setStock_actual(15);
        inventarioEjemplo.setEstado(new Estado(1, "Disponible"));
        inventarioEjemplo.setUsuarioId(1);
        
    }
    
    //@GET

    @Test
    void listar_noVacia() {
        List<Inventario> listaSimulada = List.of(inventarioEjemplo);
        when(inventarioRepository.findAll()).thenReturn(listaSimulada);

        List<Inventario> resultado = inventarioService.listarInventarios();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty(), "La lista no debería estar vacía");
        assertEquals(1, resultado.size(), "La lista debería tener exactamente 1 elemento");
        assertEquals(15, resultado.get(0).getStock_actual());
        assertEquals(1, resultado.get(0).getId());
    }

    @Test
    void listar_vacia() {
        when(inventarioRepository.findAll()).thenReturn(new ArrayList<>());

        List<Inventario> resultado = inventarioService.listarInventarios();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty(), "La lista debería estar vacía si la BD no tiene registros");
        assertEquals(0, resultado.size());
    }


    //@GET por ID

    @Test
    void buscarPorId_encontrado(){
        Optional<Inventario> inventarioOptional = Optional.of(inventarioEjemplo);
        when(inventarioRepository.findById(1)).thenReturn(inventarioOptional);



        Inventario resultado = inventarioService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals(15, resultado.getStock_actual());

    }

    @Test
    void buscarPorId_noEncontrado(){
        Optional<Inventario> optionalVacio = Optional.empty();
        when(inventarioRepository.findById(99)).thenReturn(optionalVacio);


        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            inventarioService.buscarPorId(99);
        });
        
        assertEquals("Inventario no encontrado" , error.getMessage());

    }

    //@POST

    @Test
    void crearInventario_exitoso() {
        UsuarioDTO usuarioSimulado = new UsuarioDTO();
        usuarioSimulado.setId(1);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioSimulado);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioEjemplo);

        Inventario resultado = inventarioService.crearInventario(inventarioEjemplo);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals(15, resultado.getStock_actual());
        verify(usuarioClient, times(1)).obtenerUsuario(1);
        verify(inventarioRepository, times(1)).save(inventarioEjemplo);
    }

    @Test
    void crearInventario_usuarioNoExiste() {
        when(usuarioClient.obtenerUsuario(1)).thenReturn(null);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            inventarioService.crearInventario(inventarioEjemplo);
        });

        assertEquals("Usuario no existe", error.getMessage());
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    //@PATCH por ID

    @Test
    void actualizarStockPorId_encontrado() {
        when(inventarioRepository.existsById(1)).thenReturn(true);
        when(inventarioRepository.findById(1)).thenReturn(Optional.of(inventarioEjemplo));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioEjemplo);

        inventarioService.actualizarStockPorId(1, 50);

        // Verificamos que cambió el valor interno antes de guardarse
        assertEquals(50, inventarioEjemplo.getStock_actual());
        verify(inventarioRepository, times(1)).save(inventarioEjemplo);
    }

    @Test
    void actualizarStockPorId_noEncontrado() {
        when(inventarioRepository.existsById(99)).thenReturn(false);

        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            inventarioService.actualizarStockPorId(99, 50);
        });

        assertEquals("Inventario no existe", error.getMessage());
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }
}
