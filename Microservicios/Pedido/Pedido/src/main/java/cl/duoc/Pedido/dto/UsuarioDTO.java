package cl.duoc.Pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {

    private Integer idUsuario;

    private String nombre;

    private String correo;

    private String rol;


}
