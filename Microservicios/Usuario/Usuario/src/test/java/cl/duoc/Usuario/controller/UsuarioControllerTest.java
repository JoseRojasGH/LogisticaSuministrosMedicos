package cl.duoc.Usuario.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import cl.duoc.Usuario.model.Rol;
import cl.duoc.Usuario.model.Usuario;
import cl.duoc.Usuario.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc llamadaFalsa;

    @MockitoBean
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
    void listarUsuarios_retorna200() throws Exception {
        when(usuarioService.listarUsuarios()).thenReturn(List.of(usuarioEjemplo));

        llamadaFalsa.perform(get("/api/v1/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].nombre").value("Roberto Salinas"));
    }

    @Test
    void listarUsuarios_retorna204() throws Exception {
        when(usuarioService.listarUsuarios()).thenReturn(new ArrayList<>());

        llamadaFalsa.perform(get("/api/v1/usuarios"))
            .andExpect(status().isNoContent());
    }

    // @GET por ID

    @Test
    void buscarporId_retorna200() throws Exception {
        when(usuarioService.buscarporId(1)).thenReturn(usuarioEjemplo);

        llamadaFalsa.perform(get("/api/v1/usuarios/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Roberto Salinas"));
    }

    @Test
    void buscarporId_retorna404() throws Exception {
        when(usuarioService.buscarporId(99))
            .thenThrow(new RuntimeException("Usuario no encontrado"));

        llamadaFalsa.perform(get("/api/v1/usuarios/id/99"))
            .andExpect(status().isNotFound());
    }

    // @GET por Correo

    @Test
    void buscarporCorreo_retorna200() throws Exception {
        when(usuarioService.buscarporCorreo("roberto@gmail.com")).thenReturn(usuarioEjemplo);

        llamadaFalsa.perform(get("/api/v1/usuarios/correo/roberto@gmail.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correo").value("roberto@gmail.com"));
    }

    @Test
    void buscarporCorreo_retorna404() throws Exception {
        when(usuarioService.buscarporCorreo("noexiste@gmail.com"))
            .thenThrow(new RuntimeException("Usuario no encontrado"));

        llamadaFalsa.perform(get("/api/v1/usuarios/correo/noexiste@gmail.com"))
            .andExpect(status().isNotFound());
    }

    // @POST

    @Test
    void crearUsuario_retorna200() throws Exception {
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuarioEjemplo);

        llamadaFalsa.perform(post("/api/v1/usuarios")
                .contentType("application/json")
                .content("""
                    {
                        "nombre": "Roberto Salinas",
                        "contraseña": "pass123",
                        "correo": "roberto@gmail.com",
                        "rol": { "id": 1, "nombre": "Admin" }
                    }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Roberto Salinas"));
    }

    // @PATCH

    @Test
    void actualizarContraseña_retorna200() throws Exception {
        doNothing().when(usuarioService).actualizarContraseñaUsuario(1, "nuevaPass456");

        llamadaFalsa.perform(patch("/api/v1/usuarios/1/nuevaPass456"))
            .andExpect(status().isOk());
    }

    @Test
    void actualizarContraseña_retorna404_cuandoNoExiste() throws Exception {
        doThrow(new RuntimeException("Usuario no existe"))
            .when(usuarioService).actualizarContraseñaUsuario(99, "nuevaPass456");

        llamadaFalsa.perform(patch("/api/v1/usuarios/99/nuevaPass456"))
            .andExpect(status().isNotFound());
    }

    // @DELETE

    @Test
    void eliminarUsuario_retorna204() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(1);

        llamadaFalsa.perform(delete("/api/v1/usuarios/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void eliminarUsuario_retorna404() throws Exception {
        doThrow(new RuntimeException("Usuario no existe"))
            .when(usuarioService).eliminarUsuario(99);

        llamadaFalsa.perform(delete("/api/v1/usuarios/99"))
            .andExpect(status().isNotFound());
    }
}