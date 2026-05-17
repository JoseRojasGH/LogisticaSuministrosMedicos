package cl.duoc.Producto.controller;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Producto.dto.ProductoDTO;
import cl.duoc.Producto.dto.ProductoDetalleDTO;
import cl.duoc.Producto.model.Producto;
import cl.duoc.Producto.service.ProductoService;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = productoService.listarProductos();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id){
        try {
            Producto producto = productoService.buscarPorId(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String nombre){
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/precio/{id}")
    public ResponseEntity<Double> buscarPrecioPorId(@PathVariable Integer id){
        try {
            Double precio = productoService.buscarPrecioPorId(id);
            return ResponseEntity.ok(precio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha/{id}")
    public ResponseEntity<Date> buscarFechaVencimientoPorId(@PathVariable Integer id){
        try {
            Date fechaVencimiento = productoService.buscarFechaVencimientoPorId(id);
            return ResponseEntity.ok(fechaVencimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        return ResponseEntity.ok(productoService.crearProducto(producto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id){
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/{nuevoPrecio}")
    public ResponseEntity<Void> actualizarPrecioProducto(@PathVariable Integer id, @PathVariable Double nuevoPrecio){
        try {
            productoService.actualizarPrecioProducto(id, nuevoPrecio);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<ProductoDetalleDTO> obtenerDetalleProducto(@PathVariable Integer id){
        try {
            ProductoDetalleDTO detalle = productoService.obtenerDetalleProducto(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/dto/{id}")
    public ResponseEntity<ProductoDTO> obtenerProductoDTO(@PathVariable Integer id){
        Producto producto = productoService.buscarPorId(id);
        ProductoDTO dto = new ProductoDTO(
            producto.getId(),
            producto.getPrecio(),
            producto.getFecha_vencimiento(),
            producto.getCategoria().getTipo_producto()
        );
        return ResponseEntity.ok(dto);
    }

}
