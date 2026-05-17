package cl.duoc.Devolucion.dto;

import java.util.Date;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DespachoDTO {
    private Integer id;
    private String nombre_conductor;
    private Date fecha_entrega;
}
