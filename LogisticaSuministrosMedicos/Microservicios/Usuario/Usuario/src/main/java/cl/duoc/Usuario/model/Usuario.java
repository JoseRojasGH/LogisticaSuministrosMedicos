package cl.duoc.Usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@Schema(description = "Representa un usuario dentro del sistema")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del usuario", examples ={"2", "1"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de pila del usuario", examples={"Galidson"} )
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Clave unico del usuario", examples ={"9374839"})
    private String contraseña;

    @Column(nullable = false)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
    
}
