package cl.duoc.Proveedor.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDetalleDTO {
    private Integer id;
    private String rut;
    private String razon_social;
    private String correo_contacto;
    private UsuarioDTO usuario;
}
