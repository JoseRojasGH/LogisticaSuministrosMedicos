package cl.duoc.Pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Pedido.dto.UsuarioDTO;

@FeignClient(name = "usuarioms", url = "http://localhost:8085")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    UsuarioDTO obtenerUsuario(@PathVariable Integer id);

}

