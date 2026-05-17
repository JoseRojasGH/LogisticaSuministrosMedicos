package cl.duoc.Devolucion.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "devolucion")
public class Devolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Date fechaDevolucion;

    @Column(nullable = false)
    private String motivo;

    @Column(name = "despacho_id", nullable = false)
    private Integer despachoId;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

}
