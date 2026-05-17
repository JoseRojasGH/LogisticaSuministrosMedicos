package cl.duoc.Despacho.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Despacho.model.DespachoModel;

@Repository
public interface DespachoRepository extends JpaRepository<DespachoModel, Integer>{

    List<DespachoModel> findByClienteId(Integer clienteId);

  

}