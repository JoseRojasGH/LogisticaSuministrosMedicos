package cl.duoc.Despacho.model;


import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "despacho")
@Schema(description = "Representa un Despacho dentro del sistema")

public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del Despacho", examples = {"1", "2"})
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Identificador único del conductor", examples = {"julio perez"})
    private String nombreConductor;

    @Column(nullable = false)
    @Schema(description = "Identificador único de la fecha entrega", examples = {"02/01/01"})
    private Date fechaEntrega ;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;



    

}
