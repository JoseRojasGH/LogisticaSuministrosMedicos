package cl.duoc.Inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "estado")
@Schema(description = "Representa el estado de cada inventario del sistema")
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "identificador unico del tipo de estado", examples = {"1","2"})
    private Integer id;

    @Schema(description = "descripción del inventario en el momento", examples = {"Disponible","Agotado"})
    @Column(nullable = false)
    private String disponibilidad;
}
