package cl.duoc.Cliente.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.repository.ClienteRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ClienteRepository clienteRepository) {

        return args -> {

            if (clienteRepository.count() > 0){
                System.out.println("Ya habian datos en la base de datos.");
            }else{

                Cliente cliente1 = new Cliente(null, "12345678-9", "Juan Perez", "Calle Falsa 123", "juan.perez@email.com");
                Cliente cliente2 = new Cliente(null, "98765432-1", "Ana Munoz", "Avenida Siempre Viva 456", "ana.munoz@email.com");

                clienteRepository.save(cliente1);
                clienteRepository.save(cliente2);

                System.out.println("Datos de clientes cargados con exito");

            }
        };


    }
}