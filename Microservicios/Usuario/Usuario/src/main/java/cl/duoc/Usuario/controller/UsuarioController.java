package cl.duoc.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Usuario.dto.UsuarioDTO;
import cl.duoc.Usuario.model.Usuario;
import cl.duoc.Usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping("api/v1/usuarios")
@Tag(name = "Usuario", description = "Operacion sobre los usuarios del sistema")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Lista todos los usuarios en el sistema")
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Busca un usuario por ID",
                description = "Retorna un usuario segun el ID Proporcionado")
    @ApiResponses(value=  {@ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                          @ApiResponse(responseCode = "404",description = "Usuario no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Usuario> buscarporId(@PathVariable Integer id){
        try {
            Usuario usuario = usuarioService.buscarporId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/correo/{correo}")
    @Operation(summary = "Busca un usuario por Correo",
                description = "Retorna un usuario segun el Correo Proporcionado")
    @ApiResponses(value= {@ApiResponse(responseCode = "200", description = "Usuario encontrado"),
                          @ApiResponse(responseCode = "404",description = "Usuario no encontrado"),
                          @ApiResponse(responseCode = "500",description = "Error interno del servidor")
                          } 
                )
    public ResponseEntity<Usuario> buscarporCorreo(@PathVariable String correo){
        try {
            Usuario usuario = usuarioService.buscarporCorreo(correo);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Usuario")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario Creado"),
                           @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
                           }   
                 )
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    @PatchMapping("/{id}/{contraseña}")
    @Operation(summary = "Actualizar la contrasena del Usuario")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario Actualizado"),
                           @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
                           }
                 )
    public ResponseEntity<Void> actualizarContraseñaUsuario(@PathVariable Integer id, @PathVariable String contraseña){
        try {
            usuarioService.actualizarContraseñaUsuario(id, contraseña);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un Usuario")
     @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuario Actualizado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
                            }
                  )
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id){
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    //DTO

    @GetMapping("/dto/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioDTO(@PathVariable Integer id){
        Usuario usuario = usuarioService.buscarporId(id);
        UsuarioDTO usuarioDTO = new UsuarioDTO(
            usuario.getId(),
            usuario.getCorreo(),
            usuario.getRol().getNombre()
        );
        return ResponseEntity.ok(usuarioDTO);
    }

}