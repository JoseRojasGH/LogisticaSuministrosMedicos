package cl.duoc.Producto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import cl.duoc.Producto.dto.InventarioDTO;

@FeignClient(name = "Inventario")
public interface InventarioClient {
    @GetMapping("/api/v1/inventarios/dto/{id}")
    InventarioDTO obtenerInventario(@PathVariable("id") Integer id);
}

