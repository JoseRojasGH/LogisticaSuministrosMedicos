package cl.duoc.Cliente.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Integer id;
    private String rut;
    private String direccion;
    private String correo;
}
