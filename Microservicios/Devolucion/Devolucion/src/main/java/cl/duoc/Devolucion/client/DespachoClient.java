package cl.duoc.Devolucion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Devolucion.dto.DespachoDTO;

@FeignClient(name = "Despacho", url = "http://localhost:8085")
public interface DespachoClient {
    @GetMapping("api/v1/despachos/dto/{id}")
    DespachoDTO obtenerDespacho(@PathVariable("id") Integer id);
}
