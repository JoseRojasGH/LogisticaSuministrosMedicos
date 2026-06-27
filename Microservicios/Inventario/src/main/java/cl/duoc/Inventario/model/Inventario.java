package cl.duoc.Inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventario")
@Schema(description = "Representa el inventario del sistema")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "identificador unico del inventario", examples = {"1","2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "unidades disponibles en stock", examples = {"15","1"})
    private int stock_actual;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "Identificador del usuario asociado al inventario", example = "1")
    private Integer usuarioId;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}