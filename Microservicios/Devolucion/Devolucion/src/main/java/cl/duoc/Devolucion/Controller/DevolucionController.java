package cl.duoc.Devolucion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.Devolucion.dto.DevolucionDetalleDTO;
import cl.duoc.Devolucion.model.Devolucion;
import cl.duoc.Devolucion.service.DevolucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/devoluciones")
@Tag(name = "Devolucion", description = "Operaciones sobre las devoluciones del sistema")
public class DevolucionController {

    @Autowired
    private DevolucionService service;

    @GetMapping
    @Operation(summary = "Buscar todas las devoluciones")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devoluciones encontradas"),
        @ApiResponse(responseCode = "204", description = "No se encontraron devoluciones"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<List<Devolucion>> listarDevoluciones() {

        List<Devolucion> devoluciones = service.obtenerDevoluciones();

        if (devoluciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(devoluciones);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Busca una devolución por ID",
            description = "Retorna una devolución según el ID proporcionado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devolución encontrada"),
        @ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Devolucion> buscar(@PathVariable Integer id) {

        try {
            Devolucion devolucion = service.buscarPorId(id);
            return ResponseEntity.ok(devolucion);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear una nueva devolución")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devolución creada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Devolucion> createDevolucion(@RequestBody Devolucion devolucion) {
        try {
            return ResponseEntity.ok(service.crearDevolucion(devolucion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una devolución")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Devolución actualizada"),
        @ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Devolucion> actualizar(@PathVariable Integer id,
            @RequestBody Devolucion devolucion) {

        try {
            Devolucion actualizada = service.actualizarDevolucion(id, devolucion);
            return ResponseEntity.ok(actualizada);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una devolución")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Devolución eliminada"),
        @ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        try {
            service.eliminarDevolucion(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/detalle/{id}")
    @Operation(summary = "Obtener detalle de una devolución")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle encontrado"),
        @ApiResponse(responseCode = "404", description = "Devolución no encontrada"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    public ResponseEntity<DevolucionDetalleDTO> obtenerDetalle(@PathVariable Integer id) {

        try {
            DevolucionDetalleDTO detalle = service.obtenerDetalleDevolucion(id);
            return ResponseEntity.ok(detalle);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}