package cl.duoc.Devolucion.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Devolucion.Model.Devolucion;

@Repository
public interface DevolucionRepository extends JpaRepository<Devolucion, Integer> {
    Optional<Devolucion> findByIdDespacho(String idDespacho);

}
