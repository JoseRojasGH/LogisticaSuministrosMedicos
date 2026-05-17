package cl.duoc.Despacho.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Despacho.dto.ClienteDTO;
import cl.duoc.Despacho.dto.DespachoDetalleDTO;
import cl.duoc.Despacho.client.ClienteClient;
import cl.duoc.Despacho.model.Despacho;
import cl.duoc.Despacho.repository.DespachoRepository;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    @Autowired
    private ClienteClient clienteClient;

    
    public List<Despacho> listar() {
        return despachoRepository.findAll();
    }

    public Despacho guardar(Despacho despacho) {
    
        ClienteDTO cliente = clienteClient.obtenerCliente(despacho.getClienteId());

        if (cliente == null) {
            throw new RuntimeException("Cliente no existe");
        }


        return despachoRepository.save(despacho);
    }

    public Despacho buscarPorId(Integer id) {
        return despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));
    }

    
    public Despacho buscarPorCliente(Integer clienteId) {
        return despachoRepository.findByClienteId(clienteId);
    }

    public Despacho actualizar(Integer id, Despacho despacho) {
        Despacho existente = despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));

        existente.setNombreConductor(despacho.getNombreConductor());
        existente.setFechaEntrega(despacho.getFechaEntrega());
        existente.setClienteId(despacho.getClienteId());
        return despachoRepository.save(existente);
    }

    public void eliminar(Integer id) {
        if(!despachoRepository.existsById(id)){
            throw new RuntimeException("Despacho no encontrado");
        }
        despachoRepository.deleteById(id);
    }


    public DespachoDetalleDTO obtenerDetalleDespacho(Integer id) {
        Despacho despacho = despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));

        ClienteDTO cliente = clienteClient.obtenerCliente(despacho.getClienteId());

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        DespachoDetalleDTO dto = new DespachoDetalleDTO();

        dto.setId(despacho.getId());
        dto.setNombreConductor(despacho.getNombreConductor());
        dto.setFechaEntrega(despacho.getFechaEntrega());
        dto.setCliente(cliente);


        return dto;
    }



}

    

