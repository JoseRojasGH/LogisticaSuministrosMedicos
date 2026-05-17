package cl.duoc.Proveedor.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Proveedor.model.Proveedor;
import cl.duoc.Proveedor.repository.ProveedorRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(ProveedorRepository proveedorRepository) {
        return args -> {
            if (proveedorRepository.count() > 0) {
                System.out.println("Ya habian Datos Cargados");
            }
            else{
            Proveedor proveedor1 = new Proveedor(null,"12345678-9", "Proveedor A SA", "proveedora@ejemplo.com", 1);
            Proveedor proveedor2 = new Proveedor(null,"98765432-1", "Proveedor B Ltda", "proveedorb@ejemplo.com", 2);
            
            proveedorRepository.save(proveedor1);   
            proveedorRepository.save(proveedor2);

            System.out.println("Datos de proveedores cargados exitosamente.");
            }
        };
    }
}
