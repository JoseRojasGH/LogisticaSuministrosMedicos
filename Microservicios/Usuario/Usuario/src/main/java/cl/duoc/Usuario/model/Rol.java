package cl.duoc.Usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rol")
@Schema(description = "Representa un Rol dentro del sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Schema(description = "Identificador único del Rol", examples = {"1", "2"})
    private Integer id;

    @Column(nullable = false)
     @Schema(description = "Identificador único del Rol", examples = {"Roberto Salinas"})
    private String nombre;
}
