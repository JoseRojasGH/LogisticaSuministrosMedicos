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

@RestController
@RequestMapping ("/api/v1/despachos")
public class DespachoController {

    @Autowired
    private DespachoService service;

    @GetMapping
     public ResponseEntity<List<Despacho>> listar() {

        List<Despacho> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Despacho> buscar(@PathVariable Integer id) {

        try {
            Despacho despacho = service.buscarPorId(id);
            return ResponseEntity.ok(despacho);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Despacho> buscarPorCliente(@PathVariable Integer clienteId) {
        try {
            Despacho despacho = service.buscarPorCliente(clienteId);
            return ResponseEntity.ok(despacho);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Despacho> guardar(@RequestBody Despacho despacho) {

        try {
            Despacho nuevo = service.guardar(despacho);
            return ResponseEntity.ok(nuevo);

        } catch (Exception e) {
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