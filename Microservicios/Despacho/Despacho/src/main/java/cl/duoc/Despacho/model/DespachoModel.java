package cl.duoc.Despacho.model;


import java.util.Date;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Despacho")

public class DespachoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idDespacho;

     @Column(nullable = false)
    private String direccion ;

     @Column(nullable = false)
    private String nombreConductor ;

     @Column(nullable =  false)
    private Date fechaEntrega ;

     @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;



    

}
