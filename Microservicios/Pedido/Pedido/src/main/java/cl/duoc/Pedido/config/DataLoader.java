package cl.duoc.Pedido.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Pedido.model.Pedido;
import cl.duoc.Pedido.repository.PedidoRepository;

@Configuration
public class DataLoader {
    @Bean
    CommandLineRunner initDatabase(PedidoRepository pedidoRepository) {
        return args -> {
            if(pedidoRepository.count() > 0) {
                System.out.println("Ya habian datos cargados.");
            } else{

                Pedido pedido1 = new Pedido(null, 10, 50000, 1, 1, 1, 1);
                Pedido pedido2 = new Pedido(null, 5, 25000, 2, 2, 2, 2);
                Pedido pedido3 = new Pedido(null, 20, 100000, 2, 1, 1, 1);

                pedidoRepository.save(pedido1);
                pedidoRepository.save(pedido2);
                pedidoRepository.save(pedido3);

                System.out.println("Datos de pedidos cargados exitosamente.");

            }
        };
    }
}
