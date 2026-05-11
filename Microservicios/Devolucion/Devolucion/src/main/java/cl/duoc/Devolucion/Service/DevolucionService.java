package cl.duoc.Devolucion.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Devolucion.Model.Devolucion;
import cl.duoc.Devolucion.Repository.DevolucionRepository;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    public List<Devolucion> obtenerDevoluciones() {
        return devolucionRepository.findAll();
    }

    public Optional<Devolucion> obtenerDevolucionPorId(Integer id) {
        return devolucionRepository.findById(id);
    }

    public Optional<Devolucion> obtenerDevolucionPorIdDespacho(String idDespacho) {
        return devolucionRepository.findByIdDespacho(idDespacho);
    }

    public Devolucion crearDevolucion(Devolucion devolucion) {
        return devolucionRepository.save(devolucion);
    }

    public Devolucion actualizarDevolucion(Integer id, Devolucion devolucion) {
        devolucion.setId(id);
        return devolucionRepository.save(devolucion);
    }

    public void eliminarDevolucion(Integer id) {
        devolucionRepository.deleteById(id);
    }

}
