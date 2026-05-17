package cl.duoc.Despacho.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Despacho.Dto.ClienteDTO;

@FeignClient (name="clientems" , url ="http://localhost:8084")
public interface ClienteClient {

      @GetMapping("/api/v1/clientes/{id}")
    ClienteDTO obtenerCliente(@PathVariable Integer id);
}
