package cl.duoc.Producto.model;

import java.util.Date;

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
@Table(name = "producto")
@Schema(description = "Entidad que representa un producto en el sistema")
public class Producto {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del producto", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "vitaminas para niños")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Número de lote del producto", example = "L12345")
    private String numero_lote;

    @Column(nullable = false)
    @Schema(description = "Precio del producto", example = "19.99")
    private Double precio;

    @Column(nullable = false)
    @Schema(description = "Fecha de vencimiento del producto", example = "2024-12-31")
    private Date fecha_vencimiento;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "Identificador del usuario asociado al producto", example = "1")
    private Integer usuarioId;

    @Column(name = "inventario_id", nullable = false)
    @Schema(description = "Identificador del inventario asociado al producto", example = "1")
    private Integer inventarioId;

    @Column(name = "proveedor_id", nullable = false)
    @Schema(description = "Identificador del proveedor asociado al producto", example = "1")
    private Integer proveedorId;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @Schema(description = "Categoría a la que pertenece el producto", example = "1")
    private Categoria categoria;
}
