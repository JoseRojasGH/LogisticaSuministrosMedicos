package cl.duoc.Usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import cl.duoc.Usuario.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

}
