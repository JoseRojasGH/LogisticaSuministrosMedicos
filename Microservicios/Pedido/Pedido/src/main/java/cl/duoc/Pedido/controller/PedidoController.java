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
@RequestMapping("/api/v1/pedidos")
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
    public ResponseEntity<List<Pedido>> buscarPorProducto(@PathVariable Integer productoId) {
        List<Pedido> pedidos = service.buscarPorProducto(productoId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/despacho/{despachoId}")
    public ResponseEntity<List<Pedido>> buscarPorDespacho(@PathVariable Integer despachoId) {
        List<Pedido> pedidos = service.buscarPorDespacho(despachoId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Integer clienteId) {
        List<Pedido> pedidos = service.buscarPorCliente(clienteId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<Pedido> pedidos = service.buscarPorUsuario(usuarioId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
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