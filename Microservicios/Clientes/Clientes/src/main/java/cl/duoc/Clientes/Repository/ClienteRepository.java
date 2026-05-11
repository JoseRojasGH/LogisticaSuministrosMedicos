package cl.duoc.Clientes.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.Clientes.Model.Cliente;

@Repository
public interface  ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByRut(String rut);

    public Optional<Cliente> findById(Integer id);

}
