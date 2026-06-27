package cl.duoc.Pedido.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pedido")
@Schema(description = "Entidad que representa un pedido en el sistema")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del pedido", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Cantidad de productos en el pedido", example = "2")
    private Integer cantidad;

    @Column(nullable = false)
    @Schema(description = "Identificador del producto asociado al pedido", example = "1")
    private Integer precioTotal;


    @Column(name = "producto_id", nullable = false)
    @Schema(description = "Identificador del producto asociado al pedido", example = "1")
    private Integer productoId;

    @Column(name = "despacho_id", nullable = false)
    @Schema(description = "Identificador del despacho asociado al pedido", example = "1")
    private Integer despachoId;

    @Column(name = "cliente_id", nullable = false)
    @Schema(description = "Identificador del cliente asociado al pedido", example = "1")
    private Integer clienteId;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "Identificador del usuario asociado al pedido", example = "1")
    private Integer usuarioId;

}
