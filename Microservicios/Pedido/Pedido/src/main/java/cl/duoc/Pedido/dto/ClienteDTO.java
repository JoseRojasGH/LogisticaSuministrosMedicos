package cl.duoc.Pedido.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ClienteDTO {

        private Integer idCliente;

    private String rut;

    private String nombre;

    private String direccion;

    private String comuna;

    private String correo;

}


