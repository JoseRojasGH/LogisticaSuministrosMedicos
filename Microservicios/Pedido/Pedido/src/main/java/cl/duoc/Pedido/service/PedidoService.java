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
import cl.duoc.Pedido.dto.ProductoDTO;
import cl.duoc.Pedido.dto.UsuarioDTO;
import cl.duoc.Pedido.model.PedidoModel;
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

public List<PedidoModel> listar() {
        return repository.findAll();
    }
public PedidoModel guardar(PedidoModel pedido){

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

        public PedidoModel buscarPorId(Integer id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Pedido no encontrado"));

    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }


}
