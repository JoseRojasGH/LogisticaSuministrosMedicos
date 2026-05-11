package cl.duoc.Devolucion.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Devolucion.Model.Devolucion;
import cl.duoc.Devolucion.Service.DevolucionService;

@RestController
@RequestMapping("/devoluciones")
public class DevolucionController {

    @Autowired
    private DevolucionService devolucionService;

     @GetMapping
    public List<Devolucion> getDevoluciones() {
        return devolucionService.obtenerDevoluciones();
    }

    @GetMapping("/{id}")
    public Optional<Devolucion> getDevolucionById(@PathVariable Integer id) {
        return devolucionService.obtenerDevolucionPorId(id);
    }

    @GetMapping("/despacho/{idDespacho}")
    public Optional<Devolucion> getDevolucionByIdDespacho(@PathVariable String idDespacho) {
        return devolucionService.obtenerDevolucionPorIdDespacho(idDespacho);
    }

    @PostMapping
    public Devolucion createDevolucion(@RequestBody Devolucion devolucion) {
        return devolucionService.crearDevolucion(devolucion);
    }

    @PutMapping("/{id}")
    public Devolucion updateDevolucion(@PathVariable Integer id, @RequestBody Devolucion devolucion) {
        return devolucionService.actualizarDevolucion(id, devolucion);
    }

    @DeleteMapping("/{id}")
    public void deleteDevolucion(@PathVariable Integer id) {
        devolucionService.eliminarDevolucion(id);

}
}