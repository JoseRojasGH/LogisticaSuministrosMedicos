package cl.duoc.Pedido.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

    List<Pedido> findByProductoId(Integer productoId);

    List<Pedido> findByDespachoId(Integer despachoId);

    List<Pedido> findByClienteId(Integer clienteId);

    List<Pedido> findByUsuarioId(Integer usuarioId);

  

}
