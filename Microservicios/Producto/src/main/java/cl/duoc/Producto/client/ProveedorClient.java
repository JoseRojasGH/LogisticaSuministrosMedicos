package cl.duoc.Producto.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.Producto.dto.ProveedorDTO;

@FeignClient(name = "Proveedor")
public interface ProveedorClient {
    @GetMapping("/api/v1/proveedores/dto/{id}")
    ProveedorDTO obtenerProveedor(@PathVariable("id") Integer id);

}
