package cl.duoc.Producto.dto;

import java.util.Date;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDetalleDTO {
    private Integer id;
    private String nombre;
    private String numero_lote;
    private Double precio;
    private Date fecha_vencimiento;
    private CategoriaDTO categoria;
    private ProveedorDTO proveedor;
    private InventarioDTO inventario;
    private UsuarioDTO usuario;
}
