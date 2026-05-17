package cl.duoc.Despacho.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Despacho.dto.ClienteDTO;

@FeignClient(name = "Cliente", url = "http://localhost:8086")
public interface ClienteClient {
    @GetMapping("api/v1/clientes/dto/{id}")
    ClienteDTO obtenerCliente(@PathVariable("id") Integer id);
}
