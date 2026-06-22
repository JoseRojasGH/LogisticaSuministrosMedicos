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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedido", description = "Operaciones relacionadas con los pedidos del sistema")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    @Operation(summary = "Lista todos los pedidos en el sistema")
    public ResponseEntity<List<Pedido>> listar() {

        List<Pedido> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Busca un pedido por ID",
                description = "Retorna un pedido segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Pedido encontrado"),
                           @ApiResponse(responseCode = "404",description = "Pedido no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Pedido> buscar(@PathVariable Integer id) {

        try {
            Pedido pedido = service.buscarPorId(id);
            return ResponseEntity.ok(pedido);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Busca pedidos por ID de producto",
                description = "Retorna una lista de pedidos asociados al ID de producto proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
                           @ApiResponse(responseCode = "404",description = "Pedidos no encontrados"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<List<Pedido>> buscarPorProducto(@PathVariable Integer productoId) {
        List<Pedido> pedidos = service.buscarPorProducto(productoId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/despacho/{despachoId}")
    @Operation(summary = "Busca pedidos por ID de despacho",
                description = "Retorna una lista de pedidos asociados al ID de despacho proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
                           @ApiResponse(responseCode = "404",description = "Pedidos no encontrados"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<List<Pedido>> buscarPorDespacho(@PathVariable Integer despachoId) {
        List<Pedido> pedidos = service.buscarPorDespacho(despachoId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Busca pedidos por ID de cliente",
                description = "Retorna una lista de pedidos asociados al ID de cliente proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
                           @ApiResponse(responseCode = "404",description = "Pedidos no encontrados"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Integer clienteId) {
        List<Pedido> pedidos = service.buscarPorCliente(clienteId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Busca pedidos por ID de usuario",
                description = "Retorna una lista de pedidos asociados al ID de usuario proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Pedidos encontrados"),
                           @ApiResponse(responseCode = "404",description = "Pedidos no encontrados"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<List<Pedido>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<Pedido> pedidos = service.buscarPorUsuario(usuarioId);
        if(pedidos.isEmpty()){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pedidos);
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo pedido",
                description = "Recibe un objeto Pedido en el cuerpo de la solicitud y lo guarda en el sistema")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Pedido creado exitosamente"),
                           @ApiResponse(responseCode = "400",description = "Solicitud inválida"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        return ResponseEntity.ok(service.guardar(pedido));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un pedido por ID",
                description = "Elimina un pedido del sistema segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
                           @ApiResponse(responseCode = "404",description = "Pedido no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/detalle")
    @Operation(summary = "Busca el detalle de un pedido por ID",
                description = "Retorna un objeto PedidoDetalleDTO con información detallada del pedido segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Detalle del pedido encontrado"),
                           @ApiResponse(responseCode = "404",description = "Pedido no encontrado"),
                           @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<PedidoDetalleDTO> buscarDetalle(@PathVariable Integer id) {
        try {
            PedidoDetalleDTO detalle = service.obtenerDetallePedido(id);
            return ResponseEntity.ok(detalle);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}