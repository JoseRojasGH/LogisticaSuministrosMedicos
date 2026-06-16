package cl.duoc.Cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente buscarClientePorId(Integer id) {
        return clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }


    public Cliente buscarClientePorRut(String rut) {
        return clienteRepository.findByRut(rut)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }


    public Cliente actualizarCliente(Integer id, Cliente clienteActualizado) {
        Cliente cliente = clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setRut(clienteActualizado.getRut());
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setCorreo(clienteActualizado.getCorreo());

        return clienteRepository.save(cliente);
    }

}
