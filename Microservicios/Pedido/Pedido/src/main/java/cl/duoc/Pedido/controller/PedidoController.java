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

import cl.duoc.Pedido.dto.PedidoDetalleDTO;
import cl.duoc.Pedido.model.Pedido;
import cl.duoc.Pedido.service.PedidoService;

@RestController
@RequestMapping("/api/v1/pedido")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public ResponseEntity<List<Pedido>> listar() {

        List<Pedido> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {

        try {
            Pedido pedido = service.buscarPorId(id);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Pedido> buscarPorProducto(@PathVariable Integer productoId) {
        try {
            Pedido pedido = service.buscarPorProducto(productoId);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/despacho/{despachoId}")
    public ResponseEntity<Pedido> buscarPorDespacho(@PathVariable Integer despachoId) {
        try {
            Pedido pedido = service.buscarPorDespacho(despachoId);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Pedido> buscarPorCliente(@PathVariable Integer clienteId) {
        try {
            Pedido pedido = service.buscarPorCliente(clienteId);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Pedido> buscarPorUsuario(@PathVariable Integer usuarioId) {
        try {
            Pedido pedido = service.buscarPorUsuario(usuarioId);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.guardar(pedido));
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

    @GetMapping("/{id}/detalle")
    public ResponseEntity<PedidoDetalleDTO> buscarDetalle(@PathVariable Integer id) {
        try {
            PedidoDetalleDTO detalle = service.obtenerDetallePedido(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}