package cl.duoc.Producto.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioDTO {
    private Integer id;
    private int stock_actual;
}
