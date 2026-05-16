package cl.duoc.Inventario.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Inventario.client.UsuarioClient;
import cl.duoc.Inventario.dto.*;
import cl.duoc.Inventario.model.*;
import cl.duoc.Inventario.repository.InventarioRepository;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private UsuarioClient usuarioClient;
    public List<Inventario> listarInventarios(){
        return inventarioRepository.findAll();
    }

    public Inventario crearInventario(Inventario inventario){
        UsuarioDTO usuarioDTO = usuarioClient.obtenerUsuario(inventario.getUsuarioId());
        if (usuarioDTO == null) {
            throw new RuntimeException("Usuario no existe");
        }
        return inventarioRepository.save(inventario);
    }

    public Inventario buscarPorId(Integer id){
        return inventarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    public void actualizarStockPorId(Integer id, Integer nuevoStock){
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("Inventario no existe");
        }
        inventarioRepository.updateStockById(id, nuevoStock);
    }

    public InventarioDetalleDTO obtenerDetalleInventario(Integer id){
        Inventario inventario = inventarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));


        UsuarioDTO usuario = usuarioClient.obtenerUsuario(inventario.getUsuarioId());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        Estado estado = inventario.getEstado();

        EstadoDTO estadoDTO = new EstadoDTO(estado.getId(), estado.getDisponibilidad());

        InventarioDetalleDTO dto = new InventarioDetalleDTO();
            dto.setId(inventario.getId());
            dto.setStock_actual(inventario.getStock_actual());
            dto.setUsuario(usuario);    
            dto.setEstado(estadoDTO);

        return dto;
    }
}