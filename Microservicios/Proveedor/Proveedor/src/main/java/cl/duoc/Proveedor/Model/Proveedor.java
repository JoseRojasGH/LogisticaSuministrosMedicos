package cl.duoc.Proveedor.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rut;

    @Column(nullable = false)
    private String razon_social;

    @Column(nullable = false)
    private String correo_contacto;

    
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;    
    

}
