package cl.duoc.Proveedor.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.Proveedor.Model.Proveedor;
import cl.duoc.Proveedor.Repository.ProveedorRepository;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> findAll() {
        return proveedorRepository.findAll();
    }

    public Proveedor findById(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado"));
    }

    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public Proveedor update(Long id, Proveedor proveedorData) {
        Proveedor existente = findById(id);
        existente.setNombre(proveedorData.getNombre());
        existente.setRut(proveedorData.getRut());
        existente.setEmail(proveedorData.getEmail());
        existente.setTelefono(proveedorData.getTelefono());
        existente.setDireccion(proveedorData.getDireccion());
        return proveedorRepository.save(existente);
    }

    public void delete(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Proveedor no encontrado");
        }
        proveedorRepository.deleteById(id);
    }

}
