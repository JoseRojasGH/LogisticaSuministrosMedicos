package cl.duoc.Producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.Producto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombre(String nombre);
}
