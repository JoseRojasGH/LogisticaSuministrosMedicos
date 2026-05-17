package cl.duoc.Devolucion.service;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Devolucion.client.ClienteClient;
import cl.duoc.Devolucion.client.DespachoClient;
import cl.duoc.Devolucion.dto.ClienteDTO;
import cl.duoc.Devolucion.dto.DespachoDTO;
import cl.duoc.Devolucion.dto.DevolucionDetalleDTO;
import cl.duoc.Devolucion.model.Devolucion;
import cl.duoc.Devolucion.repository.DevolucionRepository;

@Service
public class DevolucionService {

    @Autowired
    private DevolucionRepository devolucionRepository;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private DespachoClient despachoClient;

    public List<Devolucion> obtenerDevoluciones() {
        return devolucionRepository.findAll();
    }

    public Devolucion buscarPorId(Integer id) {
        return devolucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devolucion no encontrada"));
    }

    public List<Devolucion> buscarPorFecha(Date fecha_devolucion){
        if(devolucionRepository.findByFecha(fecha_devolucion).isEmpty()){
            throw new RuntimeException("Devolucion no encontrada");
        }

        return devolucionRepository.findByFecha(fecha_devolucion);
    }

    public Devolucion crearDevolucion(Devolucion devolucion) {
        ClienteDTO cliente = clienteClient.obtenerCliente(devolucion.getClienteId());

        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        DespachoDTO despacho = despachoClient.obtenerDespacho(devolucion.getDespachoId());

        if (despacho == null) {
            throw new RuntimeException("Despacho no encontrado");
        }

        return devolucionRepository.save(devolucion);
    }

    public Devolucion actualizarDevolucion(Integer id, Devolucion devolucion) {
        
        Devolucion existente = devolucionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Devolucion no encontrada"));

        existente.setFechaDevolucion(devolucion.getFechaDevolucion());
        existente.setMotivo(devolucion.getMotivo());
        existente.setDespachoId(devolucion.getDespachoId());
        existente.setClienteId(devolucion.getClienteId());

        return devolucionRepository.save(existente);
    }

    public void eliminarDevolucion(Integer id) {
        if (!devolucionRepository.existsById(id)) {
            throw new RuntimeException("Devolucion no existe");
        }
        devolucionRepository.delete(devolucionRepository.findById(id).get());
    }

    public DevolucionDetalleDTO obtenerDetalleDevolucion(Integer id){
        Devolucion devolucion = devolucionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Devolucion no encontrada"));

        ClienteDTO cliente = clienteClient.obtenerCliente(devolucion.getClienteId());

        if(cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        DespachoDTO despacho = despachoClient.obtenerDespacho(devolucion.getDespachoId());

        if(despacho == null) {
            throw new RuntimeException("Despacho no encontrado");
        }

        DevolucionDetalleDTO dto = new DevolucionDetalleDTO();
        dto.setId(devolucion.getId());
        dto.setFechaDevolucion(devolucion.getFechaDevolucion());
        dto.setMotivo(devolucion.getMotivo());
        dto.setCliente(cliente);
        dto.setDespacho(despacho);

        return dto;
    }

}