package cl.duoc.Pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Pedido.model.PedidoModel;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Integer>{

    List<PedidoModel> findByproductoId(Integer clienteId);

    List<PedidoModel> findBydespachoId(Integer clienteId);

    List<PedidoModel> findByclienteId(Integer clienteId);

    List<PedidoModel> findByusuarioId(Integer clienteId);

  

}
