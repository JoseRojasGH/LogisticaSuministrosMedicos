package cl.duoc.Devolucion.dto;

import java.util.Date;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DevolucionDetalleDTO {
    private Integer id;
    private Date fechaDevolucion;
    private String motivo;
    private ClienteDTO cliente;
    private DespachoDTO despacho;
}
