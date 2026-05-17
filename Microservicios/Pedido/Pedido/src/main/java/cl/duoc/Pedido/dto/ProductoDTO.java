package cl.duoc.Pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTO {

    private Integer idProducto;

    private String nombreProducto;

    private Integer stock;

    private Double precio;


}
