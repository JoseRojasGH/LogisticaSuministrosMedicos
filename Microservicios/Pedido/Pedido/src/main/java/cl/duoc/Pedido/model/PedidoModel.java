package cl.duoc.Pedido.model;

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
@Table(name = "Pedido")

public class PedidoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido ;

    @Column(nullable = false)
    private Integer cantidad ;

    @Column(nullable = false)
    private Integer precioTotal ;


    @Column(name = "producto_id", nullable = false)
    private Integer productoId;

    @Column(name = "despacho_id", nullable = false)
    private Integer despachoId;

    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

}
