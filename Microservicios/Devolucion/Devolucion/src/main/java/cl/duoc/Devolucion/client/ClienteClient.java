package cl.duoc.Devolucion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Devolucion.dto.ClienteDTO;

@FeignClient(name = "Cliente")
public interface ClienteClient {
    @GetMapping("api/v1/clientes/dto/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Integer id);
}
