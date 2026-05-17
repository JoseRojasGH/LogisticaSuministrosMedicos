package cl.duoc.Pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Pedido.dto.DespachoDTO;

@FeignClient(name = "Despacho", url = "http://localhost:8084")
public interface DespachoClient {
    @GetMapping("/api/v1/despacho/dto/{id}")
    DespachoDTO obtenerDespacho(@PathVariable Integer id);

}