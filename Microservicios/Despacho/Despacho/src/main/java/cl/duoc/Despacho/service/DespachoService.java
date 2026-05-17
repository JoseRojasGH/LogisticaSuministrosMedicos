package cl.duoc.Despacho.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Despacho.Dto.ClienteDTO;
import cl.duoc.Despacho.client.ClienteClient;
import cl.duoc.Despacho.model.DespachoModel;
import cl.duoc.Despacho.repository.DespachoRepository;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository repository;

    @Autowired
    private ClienteClient ClienteClient;

    
public List<DespachoModel> listar() {
        return repository.findAll();
    }

public DespachoModel guardar(DespachoModel despacho) {
    
      ClienteDTO cliente = ClienteClient.obtenerCliente(despacho.getClienteId());

        if (cliente == null) {
            throw new RuntimeException("Cliente no existe");
        }


        return repository.save(despacho);
    }

    public DespachoModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado"));
    }

    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

     public List<DespachoModel> buscarPorCliente(Integer clienteId) {
        return repository.findByClienteId(clienteId);
    }


}

    

