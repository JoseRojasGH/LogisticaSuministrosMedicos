package cl.duoc.Despacho.model;


import java.util.Date;

import jakarta.persistence.*;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "despacho")

public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombreConductor ;

    @Column(nullable =  false)
    private Date fechaEntrega ;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;



    

}
