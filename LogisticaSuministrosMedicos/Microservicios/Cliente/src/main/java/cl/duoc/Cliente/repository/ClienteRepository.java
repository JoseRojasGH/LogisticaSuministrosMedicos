package cl.duoc.Cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Cliente.model.Cliente;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByRut(String rut);
}
