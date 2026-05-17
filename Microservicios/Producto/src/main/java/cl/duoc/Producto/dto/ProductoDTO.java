package cl.duoc.Producto.dto;

import java.util.Date;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Integer id;
    private Double precio;
    private Date fecha_vencimiento;
    private String categoria;
}
