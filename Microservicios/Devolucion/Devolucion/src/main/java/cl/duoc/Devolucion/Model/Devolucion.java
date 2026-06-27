package cl.duoc.Devolucion.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "devolucion")
@Schema(description = "Representa una Devolución dentro del sistema")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la devolución", examples = {"1", "2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Fecha en que se realizó la devolución", examples = {"2025-06-09"})
    private Date fechaDevolucion;

    @Column(nullable = false)
    @Schema(description = "Motivo de la devolución", examples = {"Producto defectuoso"})
    private String motivo;

    @Column(name = "despacho_id", nullable = false)
    @Schema(description = "Identificador del despacho asociado", examples = {"1"})
    private Integer despachoId;

    @Column(name = "cliente_id", nullable = false)
    @Schema(description = "Identificador del cliente asociado", examples = {"1"})
    private Integer clienteId;
}