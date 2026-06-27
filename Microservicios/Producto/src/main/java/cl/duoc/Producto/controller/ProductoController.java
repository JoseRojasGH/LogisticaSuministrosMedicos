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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/productos")
@Tag (name = "Producto", description = "Operaciones relacionadas con los productos del sistema")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @Operation(summary = "Lista todos los productos en el sistema")
    public ResponseEntity<List<Producto>> listarProductos(){
        List<Producto> productos = productoService.listarProductos();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Busca un producto por ID",
                description = "Retorna un producto segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Producto encontrado"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id){
        try {
            Producto producto = productoService.buscarPorId(id);
            return ResponseEntity.ok(producto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Busca productos por nombre",
                description = "Retorna una lista de productos que coinciden con el nombre proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Productos encontrados"),
                           @ApiResponse(responseCode = "404",description = "No se encontraron productos con el nombre proporcionado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<List<Producto>> buscarPorNombre(@PathVariable String nombre){
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/precio/{id}")
    @Operation(summary = "Busca el precio de un producto por ID",
                description = "Retorna el precio de un producto segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Precio encontrado"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Double> buscarPrecioPorId(@PathVariable Integer id){
        try {
            Double precio = productoService.buscarPrecioPorId(id);
            return ResponseEntity.ok(precio);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha/{id}")
    @Operation(summary = "Busca la fecha de vencimiento de un producto por ID",
                description = "Retorna la fecha de vencimiento de un producto segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Fecha de vencimiento encontrada"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Date> buscarFechaVencimientoPorId(@PathVariable Integer id){
        try {
            Date fechaVencimiento = productoService.buscarFechaVencimientoPorId(id);
            return ResponseEntity.ok(fechaVencimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo producto",
                description = "Agrega un nuevo producto al sistema con la información proporcionada")
    @ApiResponses(value=  {@ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
                           @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto){
        try {
            return ResponseEntity.ok(productoService.crearProducto(producto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un producto por ID",
                description = "Elimina un producto del sistema segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Void> eliminarProducto(@PathVariable Integer id){
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/{nuevoPrecio}")
    @Operation(summary = "Actualiza el precio de un producto",
                description = "Modifica el precio de un producto existente según el ID proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Precio actualizado exitosamente"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Void> actualizarPrecioProducto(@PathVariable Integer id, @PathVariable Double nuevoPrecio){
        try {
            productoService.actualizarPrecioProducto(id, nuevoPrecio);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtiene el detalle de un producto",
                description = "Retorna un DTO con el detalle completo de un producto segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Detalle obtenido exitosamente"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<ProductoDetalleDTO> obtenerDetalleProducto(@PathVariable Integer id){
        try {
            ProductoDetalleDTO detalle = productoService.obtenerDetalleProducto(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtiene un DTO de producto",
                description = "Retorna un DTO con información específica de un producto segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "DTO obtenido exitosamente"),
                           @ApiResponse(responseCode = "404",description = "Producto no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
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
