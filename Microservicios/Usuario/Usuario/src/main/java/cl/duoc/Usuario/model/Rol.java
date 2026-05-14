package main.java.cl.duoc.Usuario.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoargsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;
}
