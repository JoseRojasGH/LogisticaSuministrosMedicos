package cl.duoc.Pedido.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

    Pedido findByProductoId(Integer productoId);

    Pedido findByDespachoId(Integer despachoId);

    Pedido findByClienteId(Integer clienteId);

    Pedido findByUsuarioId(Integer usuarioId);

  

}
