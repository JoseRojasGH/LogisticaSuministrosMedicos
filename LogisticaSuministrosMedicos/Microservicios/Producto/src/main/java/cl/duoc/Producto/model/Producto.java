package cl.duoc.Producto.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String numero_lote;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Date fecha_vencimiento;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Column(name = "inventario_id", nullable = false)
    private Integer inventarioId;

    @Column(name = "proveedor_id", nullable = false)
    private Integer proveedorId;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
