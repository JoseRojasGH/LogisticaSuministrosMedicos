package cl.duoc.Pedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Pedido.dto.ProductoDTO;

@FeignClient(name = "Producto", url = "http://localhost:8083")
public interface ProductoClient {
    @GetMapping("/api/v1/producto/dto/{id}")
    ProductoDTO obtenerProducto(@PathVariable Integer id);

}