package cl.duoc.Inventario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Inventario.model.Estado;
import cl.duoc.Inventario.model.Inventario;
import cl.duoc.Inventario.repository.EstadoRepository;
import cl.duoc.Inventario.repository.InventarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(InventarioRepository inventarioRepository, EstadoRepository estadoRepository) {
        return args -> {
            if (inventarioRepository.count() > 0) {
                System.out.println("Ya habian Datos Cargados");
            }
            else{

                Estado estado1 = new Estado(null,"Disponible");
                Estado estado2 = new Estado(null,"Agotado");

                estadoRepository.save(estado1);
                estadoRepository.save(estado2);

                Inventario inventario1 = new Inventario(null, 100, 1, estado1);
                Inventario inventario2 = new Inventario(null, 0, 1, estado2);

                inventarioRepository.save(inventario1);
                inventarioRepository.save(inventario2);

                System.out.println("Datos de inventarios cargados exitosamente.");
            }
        };
    }

}



