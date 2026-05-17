package cl.duoc.Proveedor.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedores")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;
    private String rut;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    public void setEmaill(String email) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    

}
