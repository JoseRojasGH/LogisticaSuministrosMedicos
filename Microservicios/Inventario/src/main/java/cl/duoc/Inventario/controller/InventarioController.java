package cl.duoc.Inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Inventario.dto.InventarioDTO;
import cl.duoc.Inventario.dto.InventarioDetalleDTO;
import cl.duoc.Inventario.model.Inventario;
import cl.duoc.Inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {
    
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Busca todos inventario")
    @ApiResponses(value ={ @ApiResponse(responseCode = "200" , description = "inventarios encontrados"),
                        @ApiResponse(responseCode = "204" , description = "no hay inventario disponible"),
                        @ApiResponse(responseCode = "500" , description = "error intento de servidor"),
})
    public ResponseEntity<List<Inventario>> listarInventarios(){
        List<Inventario> inventarios = inventarioService.listarInventarios();
        if(inventarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca un inventario por ID",
                description = "Retorna un inventario segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Inventario encontrado"),
                          @ApiResponse(responseCode = "404",description = "Inventario no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Inventario> obtenerInventario(@PathVariable Integer id){
        try {
            Inventario inventario = inventarioService.buscarPorId(id);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo inventario",
                description = "Agrega un nuevo inventario al sistema con la información proporcionada")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Inventario creado exitosamente"),
                          @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Inventario> crearInventario(@RequestBody Inventario inventario){
        try {
            return ResponseEntity.ok(inventarioService.crearInventario(inventario));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/{nuevoStock}")
    @Operation(summary = "Actualiza el stock actual",
                description = "Modifica el stock según el ID proporcionado y el nuevo stock")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
                          @ApiResponse(responseCode = "404",description = "Inventario no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Void> actualizarStock(@PathVariable Integer id, @PathVariable Integer nuevoStock){
        try {
            inventarioService.actualizarStockPorId(id, nuevoStock);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<InventarioDetalleDTO> obtenerDetalleInventario(@PathVariable Integer id){
        try {
            InventarioDetalleDTO detalle = inventarioService.obtenerDetalleInventario(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<InventarioDTO> obtenerInventarioDTO(@PathVariable Integer id){
        Inventario inventario = inventarioService.buscarPorId(id);
        InventarioDTO inventarioDTO = new InventarioDTO(
            inventario.getId(),
            inventario.getStock_actual(),
            inventario.getEstado().getDisponibilidad()
        );
        return ResponseEntity.ok(inventarioDTO);
    }

}

/*

@RestController
@RequestMapping("/api/v1/inventarios")
public class InventarioController {
    
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "busca todos los clientes")
    @ApiResponses(value ={ @ApiResponse(responseCode = "200" , description = "producto encontrados"),
                        @ApiResponse(responseCode = "204" , description = "no Hay stock disponible"),
                        @ApiResponse(responseCode = "500" , description = "error intento de servidor"),
})
    public ResponseEntity<List<Inventario>> listarInventarios(){
        List<Inventario> inventarios = inventarioService.listarInventarios();
        if(inventarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(inventarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> obtenerInventario(@PathVariable Integer id){
        try {
            Inventario inventario = inventarioService.buscarPorId(id);
            return ResponseEntity.ok(inventario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Inventario> crearInventario(@RequestBody Inventario inventario){
        return ResponseEntity.ok(inventarioService.crearInventario(inventario));
    }

    @PatchMapping("/{id}/{nuevoStock}")
    public ResponseEntity<Void> actualizarStock(@PathVariable Integer id, @PathVariable Integer nuevoStock){
        try {
            inventarioService.actualizarStockPorId(id, nuevoStock);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<InventarioDetalleDTO> obtenerDetalleInventario(@PathVariable Integer id){
        try {
            InventarioDetalleDTO detalle = inventarioService.obtenerDetalleInventario(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<InventarioDTO> obtenerInventarioDTO(@PathVariable Integer id){
        Inventario inventario = inventarioService.buscarPorId(id);
        InventarioDTO inventarioDTO = new InventarioDTO(
            inventario.getId(),
            inventario.getStock_actual(),
            inventario.getEstado().getDisponibilidad()
        );
        return ResponseEntity.ok(inventarioDTO);
    }

}

*/
