package cl.duoc.Cliente.controller;

import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.service.ClienteService;

public class ClienteControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
    private ClienteService service;

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
    void buscarPorId_retorna200() throws Exception{

        when(service.buscarClientePorId(1)).thenReturn(clienteEjemplo);

        llamadaFalsa.perform(get("/api/v1/clientes/1")).andExpect(status().isOk());
    }
   
}
