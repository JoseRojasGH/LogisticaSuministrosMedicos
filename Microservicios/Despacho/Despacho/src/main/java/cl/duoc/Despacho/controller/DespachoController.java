package cl.duoc.Despacho.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Despacho.model.DespachoModel;
import cl.duoc.Despacho.service.DespachoService;

@RestController
@RequestMapping ("/api/v1/despachos")
public class DespachoController {

    @Autowired
    private DespachoService service;

    @GetMapping
     public ResponseEntity<List<DespachoModel>> listar() {

        List<DespachoModel> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

     @GetMapping("/{id}")
    public ResponseEntity<DespachoModel> buscar(@PathVariable Integer id) {

        try {
            DespachoModel despacho = service.buscarPorId(id);
            return ResponseEntity.ok(despacho);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

      @PostMapping
    public ResponseEntity<DespachoModel> guardar(@RequestBody DespachoModel despacho) {

        try {
            DespachoModel nuevo = service.guardar(despacho);
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

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<DespachoModel>> buscarPorCliente(@PathVariable Integer clienteId) {

        List<DespachoModel> lista = service.buscarPorCliente(clienteId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    
}
