package cl.duoc.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.Usuario.model.Usuario;
import cl.duoc.Usuario.service.UsuarioService;
import cl.duoc.Usuario.dto.UsuarioDTO;

@RestController 
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> buscarporId(@PathVariable Integer id){
        try {
            Usuario usuario = usuarioService.buscarporId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/correo/{correo}")
    public ResponseEntity<Usuario> buscarporCorreo(@PathVariable String correo){
        try {
            Usuario usuario = usuarioService.buscarporCorreo(correo);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }

    @PatchMapping("/{id}/{contraseña}")
    public ResponseEntity<Void> actualizarContraseñaUsuario(@PathVariable Integer id, @PathVariable String contraseña){
        try {
            usuarioService.actualizarContraseñaUsuario(id, contraseña);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
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
