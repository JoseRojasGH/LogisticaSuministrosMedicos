package cl.duoc.Pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Pedido.dto.ProductoDTO;

@FeignClient(name = "Producto")
public interface ProductoClient {
    @GetMapping("/api/v1/productos/dto/{id}")
    ProductoDTO obtenerProducto(@PathVariable("id") Integer id);

}