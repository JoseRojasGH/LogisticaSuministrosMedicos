package cl.duoc.Proveedor.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proveedor")  
@Schema(description = "Entidad que representa a un proveedor en el sistema")                                                    
public class Proveedor {

  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del proveedor", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "RUT del proveedor", example = "12.345.678-9")
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Razón social del proveedor", example = "Empresa S.A.")
    private String razon_social;

    @Column(nullable = false)
    @Schema(description = "Correo de contacto del proveedor", example = "contacto@empresa.cl")
    private String correo_contacto;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "Identificador del usuario asociado al proveedor", example = "1")
    private Integer usuarioId;

}
