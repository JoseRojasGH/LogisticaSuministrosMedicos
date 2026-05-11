package cl.duoc.Clientes.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.Clientes.Model.Cliente;
import cl.duoc.Clientes.Repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> obtenerAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Integer id) {
        return clienteRepository.findById(id.longValue());
    }

    public Optional<Cliente> obtenerClientePorRut(String rut) {
        return clienteRepository.findByRut(rut);
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Integer id, Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Integer id) {
        clienteRepository.deleteById(id.longValue());
    }

    public List<Cliente> getClientes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Cliente> obtenerClientes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Cliente> listarClientes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
