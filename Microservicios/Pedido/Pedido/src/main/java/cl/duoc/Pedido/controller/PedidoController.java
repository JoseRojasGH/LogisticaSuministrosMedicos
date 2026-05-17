package cl.duoc.Pedido.controller;

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

import cl.duoc.Pedido.model.PedidoModel;
import cl.duoc.Pedido.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

       @GetMapping
    public ResponseEntity<List<PedidoModel>> listar() {

        List<PedidoModel> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

     @GetMapping("/{id}")
    public ResponseEntity<PedidoModel> buscar(@PathVariable Integer id) {

        try {
            PedidoModel pedido = service.buscarPorId(id);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

      @PostMapping
    public ResponseEntity<PedidoModel> guardar(@RequestBody PedidoModel pedido) {

        try {
            PedidoModel nuevo = service.guardar(pedido);
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

}

