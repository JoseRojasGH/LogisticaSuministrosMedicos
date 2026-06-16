package cl.duoc.Devolucion.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Devolucion.model.Devolucion;
import cl.duoc.Devolucion.repository.DevolucionRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(DevolucionRepository devolucionRepository) {
        return args -> {

            if(devolucionRepository.count() > 0) {
                System.out.println("Ya habian Datos Cargados");
            }
            else{

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date fechaDevolucion1 = sdf.parse("05/05/2026");
                Date fechaDevolucion2 = sdf.parse("01/04/2026");

                Devolucion devolucion1 = new Devolucion(null, fechaDevolucion1, "Producto Dañado", 1, 2);
                Devolucion devolucion2 = new Devolucion(null, fechaDevolucion2, "Producto no cumple las Expectativas", 1, 2);

                devolucionRepository.save(devolucion1);
                devolucionRepository.save(devolucion2);

                System.out.println("Datos de devoluciones cargados exitosamente.");
            }
        };
    }

}


