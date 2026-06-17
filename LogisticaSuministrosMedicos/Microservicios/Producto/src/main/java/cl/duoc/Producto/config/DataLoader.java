package cl.duoc.Producto.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Producto.model.Categoria;
import cl.duoc.Producto.model.Producto;
import cl.duoc.Producto.repository.CategoriaRepository;
import cl.duoc.Producto.repository.ProductoRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        return args -> {

            if(productoRepository.count() > 0) {
                System.out.println("Ya habian Datos Cargados");
            }
            else{
                
                Categoria categoria1 = new Categoria(null, "Terapéutico");
                Categoria categoria2 = new Categoria(null, "Preventivo");

                categoriaRepository.save(categoria1);
                categoriaRepository.save(categoria2);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                Date fechaVencimiento1 = sdf.parse("30/06/2027");
                Date fechaVencimiento2 = sdf.parse("01/03/2027");

                Producto producto1 = new Producto(null, "Termometro", "#72632634", 20000.0, fechaVencimiento1, 1, 1, 1, categoria1);
                Producto producto2 = new Producto(null, "Estetoscopio", "#83746576", 30000.0, fechaVencimiento2, 1, 2, 2, categoria1);

                productoRepository.save(producto1);
                productoRepository.save(producto2);

                System.out.println("Datos de productos cargados exitosamente.");
            }
        };
    }
}
