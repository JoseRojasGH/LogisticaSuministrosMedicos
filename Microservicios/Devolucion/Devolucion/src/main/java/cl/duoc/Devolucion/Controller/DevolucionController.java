package cl.duoc.Devolucion.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import cl.duoc.Devolucion.dto.DevolucionDetalleDTO;
import cl.duoc.Devolucion.model.Devolucion;
import cl.duoc.Devolucion.service.DevolucionService;

@RestController
@RequestMapping("/api/v1/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

    @GetMapping
    public ResponseEntity<List<Devolucion>> getDevoluciones() {
        List<Devolucion> devoluciones = devolucionService.obtenerDevoluciones();
        if(devoluciones.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devoluciones);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Devolucion> getDevolucionById(@PathVariable Integer id) {
        try {
            Devolucion devolucion = devolucionService.buscarPorId(id);
            return ResponseEntity.ok(devolucion);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<Devolucion>> getDevolucionByFecha(@PathVariable Date fecha) {
        List<Devolucion> devolucionesPorFecha = devolucionService.buscarPorFecha(fecha);
        if(devolucionesPorFecha.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(devolucionesPorFecha);
    }

    @PostMapping
    public ResponseEntity<Devolucion> createDevolucion(@RequestBody Devolucion devolucion) {
        return ResponseEntity.ok(devolucionService.crearDevolucion(devolucion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Devolucion> updateDevolucion(@PathVariable Integer id, @RequestBody Devolucion devolucion) {
         try {
            Devolucion devolucionActualizada = devolucionService.actualizarDevolucion(id, devolucion);
            return ResponseEntity.ok(devolucionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevolucion(@PathVariable Integer id) {
        try {
            devolucionService.eliminarDevolucion(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/{id}/detalle")
    public ResponseEntity<DevolucionDetalleDTO> obtenerDetalleDevolucion(@PathVariable Integer id){
        try {
            DevolucionDetalleDTO detalle = devolucionService.obtenerDetalleDevolucion(id);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


}