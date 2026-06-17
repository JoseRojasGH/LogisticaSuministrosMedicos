package cl.duoc.Inventario.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.Inventario.model.Estado;
import cl.duoc.Inventario.model.Inventario;
import cl.duoc.Inventario.repository.InventarioRepository;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

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
}
