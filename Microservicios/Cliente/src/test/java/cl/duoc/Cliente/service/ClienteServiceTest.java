package cl.duoc.Cliente.service;

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

import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.repository.ClienteRepository;


@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest{

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente clienteEjemplo;

    @BeforeEach
    void setUp(){

        clienteEjemplo = new Cliente();
        clienteEjemplo.setId(1);
        clienteEjemplo.setRut("12345678-9");
        clienteEjemplo.setNombre("juan perez");
        clienteEjemplo.setDireccion("recoleta 123");
        clienteEjemplo.setCorreo("juanperez@gmail.com");

    }

    @Test
    void buscarClientePorId_encontrado(){

        Optional<Cliente> clienteOptional=Optional.of(clienteEjemplo);
        when(clienteRepository.findById(1)).thenReturn(clienteOptional);
        
        Cliente resultado = clienteService.buscarClientePorId(1);

        assertEquals(1, resultado.getId());
        assertEquals("juan perez", resultado.getNombre());

    }

    @Test
    void buscarClientePorId_nOencontrado(){
        
        Optional<Cliente> optionalVacio = Optional.empty();
        when(clienteRepository.findById(99)).thenReturn(optionalVacio);

        RuntimeException error = assertThrows(RuntimeException.class,()->{
        clienteService.buscarClientePorId(99);
    });

        assertEquals("Cliente no encontrado", error.getMessage());

    }
}




