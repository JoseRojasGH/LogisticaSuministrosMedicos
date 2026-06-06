package cl.duoc.Proveedor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.Proveedor.dto.UsuarioDTO;

@FeignClient(name = "Usuario")
public interface UsuarioClient {
    @GetMapping("/api/v1/usuarios/dto/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable("id") Integer id);
}
