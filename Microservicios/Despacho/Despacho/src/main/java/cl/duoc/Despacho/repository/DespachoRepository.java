package cl.duoc.Despacho.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Despacho.model.Despacho;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Integer>{

    Despacho findByClienteId(Integer clienteId);

}