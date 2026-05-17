package cl.duoc.Usuario.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.duoc.Usuario.model.Rol;
import cl.duoc.Usuario.model.Usuario;
import cl.duoc.Usuario.repository.RolRepository;
import cl.duoc.Usuario.repository.UsuarioRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        return args -> {
            if (usuarioRepository.count() > 0) {
                System.out.println("Ya habian Datos Cargados");
            }
            else{
            Rol rol1 = new Rol(null,"Administrador");
            Rol rol2 = new Rol(null,"Usuario");

            rolRepository.save(rol1);
            rolRepository.save(rol2);

            Usuario usuario1 = new Usuario(null,"Juan Perez", "12345", "juan.perez@duoc.cl" , rol2);
            Usuario usuario2 = new Usuario(null,"Maria Gomez", "67890", "maria.gomez@duoc.cl", rol1);

            usuarioRepository.save(usuario1);
            usuarioRepository.save(usuario2);

            System.out.println("Datos de usuarios cargados exitosamente.");

            }
        };
        
    }

}
