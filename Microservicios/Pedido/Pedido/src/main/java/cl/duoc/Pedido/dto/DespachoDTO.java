package cl.duoc.Pedido.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DespachoDTO {

    private Integer idDespacho;

    private String direccion;

    private String nombreConductor;

    private Date fechaEntrega;


}
