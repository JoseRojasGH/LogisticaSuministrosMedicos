package cl.duoc.Inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.Inventario.dto.UsuarioDTO;

@FeignClient(name = "Usuario", url = "http://localhost:8081")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/dto/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable("id") Integer id);
}
