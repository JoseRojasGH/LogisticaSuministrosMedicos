package cl.duoc.Pedido.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTO {
    private Integer id;
    private Double precio;
    private Date fecha_vencimiento;
    private String categoria;
}
