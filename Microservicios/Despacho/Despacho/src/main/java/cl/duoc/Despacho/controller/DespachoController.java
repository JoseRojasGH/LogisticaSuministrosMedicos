package cl.duoc.Despacho.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Despacho.dto.DespachoDTO;
import cl.duoc.Despacho.dto.DespachoDetalleDTO;
import cl.duoc.Despacho.model.Despacho;
import cl.duoc.Despacho.service.DespachoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping ("/api/v1/despachos")
@Tag(name = "Despacho", description = "Operacion sobre los despachos del sistema")
public class DespachoController {

    @Autowired
    private DespachoService service;

    @GetMapping
    @Operation(summary = "Buscar todos los despachos")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Despacho Encontrado"),
                            @ApiResponse(responseCode = "204", description = "No se encontraron los Despacho"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })

     public ResponseEntity<List<Despacho>> listar() {

        List<Despacho> despachos = service.listar();

        if (despachos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(despachos);
    }

    @GetMapping("/id/{id}")
     @Operation(summary = "Busca un Despacho por ID", 
                description = "Retorna un Despacho según el ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Despacho Encontrado"),
                            @ApiResponse(responseCode = "404", description = "Despacho no Encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
})

    public ResponseEntity<Despacho> buscar(@PathVariable Integer id) {

        try {
            Despacho despacho = service.buscarPorId(id);
            return ResponseEntity.ok(despacho);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
      @Operation(summary = "Busca un Despacho por el cliente", 
                description = "Retorna un Despacho según el Cliente proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente Encontrado"),
                            @ApiResponse(responseCode = "404", description = "Cliente no Encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
     public ResponseEntity<List<Despacho>> getDespachoByCliente(
        @PathVariable("clienteId") Integer clienteId) {

    try {
        List<Despacho> despachos = service.buscarPorCliente(clienteId);
        return ResponseEntity.ok(despachos);

    } catch (RuntimeException e) {
        return ResponseEntity.notFound().build();
    }
}

    @PostMapping
    @Operation(summary = "Crea un nuevo Despacho")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Despacho Creado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    public ResponseEntity<Despacho> guardar(@RequestBody Despacho despacho) {
        try {
            Despacho nuevo = service.guardar(despacho);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despacho> actualizar(@PathVariable Integer id, @RequestBody Despacho despacho) {
        try {
            Despacho actualizado = service.actualizar(id, despacho);
            return ResponseEntity.ok(actualizado);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/detalle/{id}")
    public ResponseEntity<DespachoDetalleDTO> obtenerDetalle(@PathVariable Integer id) {
        try {
            DespachoDetalleDTO detalle = service.obtenerDetalleDespacho(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<DespachoDTO> obtenerDetalleDTO(@PathVariable Integer id) {
        Despacho despacho = service.buscarPorId(id);

        DespachoDTO dto = new DespachoDTO(
            despacho.getId(),
            despacho.getNombreConductor(),
            despacho.getFechaEntrega()
        );
        return ResponseEntity.ok(dto);
    }

}