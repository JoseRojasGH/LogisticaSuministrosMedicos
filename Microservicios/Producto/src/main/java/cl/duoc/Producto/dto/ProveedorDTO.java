package cl.duoc.Producto.dto;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDTO {
    private Integer id;
    private String rut;
    private String razon_social;
}
