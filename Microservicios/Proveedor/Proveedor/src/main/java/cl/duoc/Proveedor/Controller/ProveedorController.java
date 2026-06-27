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


@RestController
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar() {
        List<Proveedor> proveedores = proveedorService.listarProveedores();

        if(proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Proveedor> obtenerProveedorPorId(@PathVariable Integer id) {
        try {
            Proveedor proveedor = proveedorService.buscarPorId(id);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Proveedor> obtenerProveedorPorRut(@PathVariable String rut) {
        try {
            Proveedor proveedor = proveedorService.buscarPorRut(rut);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        try {
            return ResponseEntity.ok(proveedorService.crearProveedor(proveedor));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Integer id, @RequestBody Proveedor proveedorActualizado) {
        try {
            Proveedor proveedor = proveedorService.actualizarProveedor(id, proveedorActualizado);
            return ResponseEntity.ok(proveedor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/{id}/detalle")
    public ResponseEntity<ProveedorDetalleDTO> obtenerDetalleProveedor(@PathVariable Integer id) {
        try {
            ProveedorDetalleDTO dto = proveedorService.obtenerDetalleProveedor(id);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();   
        }
    }
    


    @GetMapping("/dto/{id}")
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
