package cl.duoc.Proveedor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Proveedor.client.UsuarioClient;
import cl.duoc.Proveedor.dto.ProveedorDetalleDTO;
import cl.duoc.Proveedor.dto.UsuarioDTO;
import cl.duoc.Proveedor.model.Proveedor;
import cl.duoc.Proveedor.repository.ProveedorRepository;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public Proveedor buscarPorRut(String rut) {
        return proveedorRepository.findByRut(rut)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(proveedor.getUsuarioId());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }
        return proveedorRepository.save(proveedor);
    }

    public Proveedor actualizarProveedor(Integer id, Proveedor proveedorActualizado) {
        Proveedor existente = proveedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        existente.setRut(proveedorActualizado.getRut());
        existente.setRazon_social(proveedorActualizado.getRazon_social());
        existente.setCorreo_contacto(proveedorActualizado.getCorreo_contacto());
        existente.setUsuarioId(proveedorActualizado.getUsuarioId());

        return proveedorRepository.save(existente);
    }

    public ProveedorDetalleDTO obtenerDetalleProveedor(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(proveedor.getUsuarioId());

        if(usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        ProveedorDetalleDTO dto = new ProveedorDetalleDTO();
        dto.setId(proveedor.getId());
        dto.setRut(proveedor.getRut());
        dto.setRazon_social(proveedor.getRazon_social());
        dto.setCorreo_contacto(proveedor.getCorreo_contacto());
        dto.setUsuario(usuario);

        return dto;
    }


}
