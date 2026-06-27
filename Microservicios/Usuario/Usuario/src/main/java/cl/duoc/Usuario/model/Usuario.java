package cl.duoc.Usuario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
@Schema(description = "Representa un Usuario dentro del sistema")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del Usuario", examples = {"1", "2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Identificador único del Usuario", examples = {"Roberto Salinas"})
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Clave unica para iniciar sesion del Usuario", examples = {"Sal2025"})
    private String contraseña;

    @Column(nullable = false)
    @Schema(description = "Correo único del Usuario", examples = {"example@gmail.com"})
    private String correo;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;
    
}
