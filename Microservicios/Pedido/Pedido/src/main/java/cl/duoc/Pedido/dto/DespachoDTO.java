package cl.duoc.Pedido.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DespachoDTO {
    private Integer id;
    private String nombreConductor;
    private Date fechaEntrega;
}
