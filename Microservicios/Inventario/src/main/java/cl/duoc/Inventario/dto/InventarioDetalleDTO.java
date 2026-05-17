package cl.duoc.Inventario.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDetalleDTO {
    private Integer id;
    private int stock_actual;
    private UsuarioDTO usuario;
    private EstadoDTO estado;
}
