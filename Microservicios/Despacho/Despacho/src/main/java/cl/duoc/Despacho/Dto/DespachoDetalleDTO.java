package cl.duoc.Despacho.dto;

import java.util.Date;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespachoDetalleDTO {
    private Integer id;
    private String nombreConductor ;
    private Date fechaEntrega ;
    private ClienteDTO cliente;
}
