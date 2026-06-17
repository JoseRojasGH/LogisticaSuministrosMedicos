package cl.duoc.Despacho.config;

import cl.duoc.Despacho.repository.DespachoRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Despacho.model.Despacho;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initData(DespachoRepository despachoRepository) {
        return args -> {
            if(despachoRepository.count() > 0) {
                System.out.println("Ya habian Datos Cargados");
            }
            else{
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date fechaDespacho1 = sdf.parse("12/03/2026");
                Date fechaDespacho2 = sdf.parse("21/04/2026");

                Despacho d1 = new Despacho(null, "Juan Perez", fechaDespacho1, 1);
                Despacho d2 = new Despacho(null, "Maria Gomez", fechaDespacho2, 2);

                despachoRepository.save(d1);
                despachoRepository.save(d2);

                System.out.println("Datos de despachos cargados exitosamente.");
            }
        };
    }
}
