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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operacion sobre los clientes del sistema")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @Operation(summary = "Busca todos los Clientes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Clientes Encontrados"),
                            @ApiResponse(responseCode = "204", description = "No se encontraron Clientes"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    public ResponseEntity<List<Cliente>> listarClientes(){
        List<Cliente> clientes = clienteService.listarClientes();

        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Busca un Cliente por ID", 
                description = "Retorna un Cliente según el ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente Encontrado"),
                            @ApiResponse(responseCode = "404", description = "Cliente no Encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id){
        try {
            Cliente cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Busca un Cliente por RUT", 
                description = "Retorna un Cliente según el RUT proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente Encontrado"),
                            @ApiResponse(responseCode = "404", description = "Cliente no Encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    public ResponseEntity<Cliente> getClienteByRut(@PathVariable String rut){
        try {
            Cliente cliente = clienteService.buscarClientePorRut(rut);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo Cliente")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente Creado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente){
        return ResponseEntity.ok(clienteService.crearCliente(cliente));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modifica un Cliente", 
                description = "Modifica un Cliente según el ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente se ha Modificado"),
                            @ApiResponse(responseCode = "404", description = "Cliente no Encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
    public ResponseEntity<Cliente> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente){
        try {
            Cliente clienteActualizado = clienteService.actualizarCliente(id, cliente);
            return ResponseEntity.ok(clienteActualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Busca la información del DTO de un cliente", 
                description = "Retorna la información importante de un Cliente según el ID proporcionado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Cliente Encontrado"),
                            @ApiResponse(responseCode = "500", description = "Error Interno del Servidor")
    })
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
