package cl.duoc.Pedido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Pedido.client.ClienteClient;
import cl.duoc.Pedido.client.DespachoClient;
import cl.duoc.Pedido.client.ProductoClient;
import cl.duoc.Pedido.client.UsuarioClient;
import cl.duoc.Pedido.dto.ClienteDTO;
import cl.duoc.Pedido.dto.DespachoDTO;
import cl.duoc.Pedido.dto.PedidoDetalleDTO;
import cl.duoc.Pedido.dto.ProductoDTO;
import cl.duoc.Pedido.dto.UsuarioDTO;
import cl.duoc.Pedido.model.Pedido;
import cl.duoc.Pedido.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private ProductoClient productoClient;
    
    @Autowired
    private DespachoClient despachoClient;

    @Autowired
    private ClienteClient clienteClient;
    
    @Autowired
    private UsuarioClient usuarioClient;

    public List<Pedido> listar() {
        return repository.findAll();
    }

    public Pedido guardar(Pedido pedido){

        ClienteDTO cliente = 
            clienteClient.obtenerCliente(pedido.getClienteId());

        if (cliente == null) {
            throw new RuntimeException("Cliente no exite");
        }

        UsuarioDTO usuario =
                usuarioClient.obtenerUsuario(pedido.getUsuarioId());
    
        if (usuario == null) {
            throw new RuntimeException("Usuario no existe");
        }

        ProductoDTO producto =
            productoClient.obtenerProducto(pedido.getProductoId());

        if (producto == null) {
            throw new RuntimeException("Producto no existe");
        }
        
        DespachoDTO despacho =
            despachoClient.obtenerDespacho(pedido.getDespachoId());

        if (despacho == null) {
            throw new RuntimeException("Despacho no existe");
        }

        return repository.save(pedido);
    }

    public Pedido buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

    }

    public Pedido buscarPorCliente(Integer clienteId) {
        return repository.findByClienteId(clienteId);
    }

    public Pedido buscarPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public Pedido buscarPorProducto(Integer productoId) {
        return repository.findByProductoId(productoId);
    }

    public Pedido buscarPorDespacho(Integer despachoId) {
        return repository.findByDespachoId(despachoId);
    }

    public Pedido actualizar(Integer id, Pedido pedido) {
        Pedido existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        existente.setCantidad(pedido.getCantidad());
        existente.setPrecioTotal(pedido.getPrecioTotal());
        existente.setProductoId(pedido.getProductoId());
        existente.setDespachoId(pedido.getDespachoId());
        existente.setClienteId(pedido.getClienteId());
        existente.setUsuarioId(pedido.getUsuarioId());

        return repository.save(existente);
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    public PedidoDetalleDTO obtenerDetallePedido(Integer id) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        ClienteDTO cliente = clienteClient.obtenerCliente(pedido.getClienteId());

        if(cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(pedido.getUsuarioId());

        if(usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        ProductoDTO producto = productoClient.obtenerProducto(pedido.getProductoId());

        if(producto == null) {
            throw new RuntimeException("Producto no encontrado");
        }

        DespachoDTO despacho = despachoClient.obtenerDespacho(pedido.getDespachoId());

        if(despacho == null) {
            throw new RuntimeException("Despacho no encontrado");
        }

        PedidoDetalleDTO dto = new PedidoDetalleDTO();
        dto.setId(pedido.getId());
        dto.setCantidad(pedido.getCantidad());
        dto.setPrecioTotal(pedido.getPrecioTotal());
        dto.setCliente(cliente);
        dto.setUsuario(usuario);
        dto.setProducto(producto);
        dto.setDespacho(despacho);

        return dto;
    }


}
