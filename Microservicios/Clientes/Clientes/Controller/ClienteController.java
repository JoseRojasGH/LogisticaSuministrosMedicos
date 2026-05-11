package cl.duoc.Clientes.Controller;

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

import cl.duoc.Clientes.Model.Cliente;
import cl.duoc.Clientes.Service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getClientes(){
        return clienteService.obtenerClientes();
    }

    @GetMapping("/{id}")
    public Optional<Cliente> getClienteById(@PathVariable Integer id){
        return clienteService.obtenerClientePorId(id);
    }

    @GetMapping("/rut/{rut}")
    public Optional<Cliente> getClienteByRut(@PathVariable String rut){
        return clienteService.obtenerClientePorRut(rut);
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente){
        return clienteService.crearCliente(cliente);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente){
        return clienteService.actualizarCliente(id, cliente);
    }

    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Integer id){
        clienteService.eliminarCliente(id);
    }

}
