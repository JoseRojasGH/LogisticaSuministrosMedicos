package cl.duoc.Proveedor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Proveedor.dto.ProveedorDTO;
import cl.duoc.Proveedor.dto.ProveedorDetalleDTO;
import cl.duoc.Proveedor.model.Proveedor;
import cl.duoc.Proveedor.service.ProveedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/proveedores")
@Tag(name = "Proveedor", description = "Operaciones relacionadas con los proveedores del sistema")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @Operation(summary = "Lista todos los proveedores en el sistema")
    public ResponseEntity<List<Proveedor>> listar() {
        List<Proveedor> proveedores = proveedorService.listarProveedores();

        if(proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Busca un proveedor por ID",
                description = "Retorna un proveedor segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
                          @ApiResponse(responseCode = "404",description = "Proveedor no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Integer id) {
        try {
            Proveedor proveedor = proveedorService.buscarPorId(id);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Busca un proveedor por RUT",
                description = "Retorna un proveedor segun el RUT Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Proveedor encontrado"),
                          @ApiResponse(responseCode = "404",description = "Proveedor no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Proveedor> obtenerProveedorPorRut(@PathVariable String rut) {
        try {
            Proveedor proveedor = proveedorService.buscarPorRut(rut);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo proveedor",
                description = "Agrega un nuevo proveedor al sistema con la información proporcionada")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Proveedor creado exitosamente"),
                          @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        try {
            return ResponseEntity.ok(proveedorService.crearProveedor(proveedor));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza un proveedor existente",
                description = "Modifica la información de un proveedor existente según el ID proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Proveedor actualizado exitosamente"),
                          @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
                          @ApiResponse(responseCode = "404",description = "Proveedor no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer id, @RequestBody Proveedor proveedorActualizado) {
        try {
            Proveedor proveedor = proveedorService.actualizarProveedor(id, proveedorActualizado);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/detalle")
    @Operation(summary = "Obtiene el detalle de un proveedor",
                description = "Retorna un DTO con información detallada del proveedor, incluyendo datos relacionados")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Detalle del proveedor obtenido exitosamente"),
                          @ApiResponse(responseCode = "404",description = "Proveedor no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<ProveedorDetalleDTO> obtenerDetalleProveedor(@PathVariable Integer id) {
        try {
            ProveedorDetalleDTO dto = proveedorService.obtenerDetalleProveedor(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();   
        }
    }
    


    @GetMapping("/dto/{id}")
    @Operation(summary = "Busca la información del DTO de un proveedor",
                description = "Retorna un DTO con información básica del proveedor según el ID proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "DTO del proveedor obtenido exitosamente"),
                          @ApiResponse(responseCode = "404",description = "Proveedor no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<ProveedorDTO> obtenerProveedorDTO(@PathVariable Integer id) {
        Proveedor proveedor = proveedorService.buscarPorId(id);

        ProveedorDTO dto = new ProveedorDTO(
            proveedor.getId(),
            proveedor.getRut(),
            proveedor.getRazon_social()
        );
        return ResponseEntity.ok(dto);
    }

    

}
