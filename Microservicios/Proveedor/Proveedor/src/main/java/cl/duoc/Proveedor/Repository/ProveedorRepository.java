package cl.duoc.Proveedor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Proveedor.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByRut(String rut);
}
