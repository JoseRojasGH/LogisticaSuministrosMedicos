package cl.duoc.Cliente.model;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cliente")
@Schema(description = "Representa un Cliente dentro del sistema")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del Cliente", examples = {"1", "2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Rol Unico Tributario del Cliente", examples = {"12345678-9"})
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Identificador único del Cliente", examples = {"Roberto Salinas"})
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Identificador único del Cliente", examples = {"Avenida Siempre Viva 123"})
    private String direccion;

    @Column(nullable = false)
    @Schema(description = "Identificador único del Cliente", examples = {"example@gmail.com"})
    private String correo;
}
