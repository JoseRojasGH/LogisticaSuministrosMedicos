package cl.duoc.Cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.Cliente.dto.ClienteDTO;
import cl.duoc.Cliente.model.Cliente;
import cl.duoc.Cliente.service.ClienteService;


@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes = clienteService.listarClientes();

        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id){
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<Cliente> getClienteByRut(@PathVariable String rut){
        try {
            Cliente cliente = clienteService.buscarClientePorRut(rut);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente){
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ClienteDTO> obtenerClienteDTO(@PathVariable Integer id) {
        Cliente cliente = clienteService.buscarClientePorId(id);

        ClienteDTO dto = new ClienteDTO(
            cliente.getId(),
            cliente.getRut(),
            cliente.getDireccion(),
            cliente.getCorreo()
        );
        return ResponseEntity.ok(dto);
    }
    


}
