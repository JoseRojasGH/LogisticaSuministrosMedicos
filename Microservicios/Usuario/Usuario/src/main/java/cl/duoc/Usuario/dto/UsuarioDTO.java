package main.java.cl.duoc.Usuario.dto;

import lombok.*;

@Data
@NoargsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String contraseña;
    private String correo;
    private String rol;
}
