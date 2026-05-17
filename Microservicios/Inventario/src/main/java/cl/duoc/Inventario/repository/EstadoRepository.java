package cl.duoc.Inventario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Inventario.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

}
