package cl.duoc.Pedido.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalleDTO {
    private Integer id;
    private Integer cantidad;
    private Integer precioTotal;
    private ProductoDTO producto;
    private DespachoDTO despacho;
    private ClienteDTO cliente;
    private UsuarioDTO usuario;
}
