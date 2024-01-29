package com.example.demo.web.ws;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ws/clientes")
public class ClienteRestController {private static final Logger log =
        LoggerFactory.getLogger(ClienteRestController.class);
    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    // Listar los clientes
    @RequestMapping(method = RequestMethod.GET)
    public List<ClienteDTO> findAll() {

        log.info("ClienteRestController - findAll: Mostramos todos los clientes");

        List<ClienteDTO> listaClientesDTO = clienteService.findAll();
        return listaClientesDTO;
    }

    // Visualizar la informacion de un cliente
    @GetMapping(path = "/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable("id") Long id) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(id);
        Optional<ClienteDTO> opt = Optional.ofNullable(clienteService.findById(clienteDTO));
        if(opt.isEmpty()){
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(opt.get());
        }
    }

    //Actualizar un cliente

    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Long id, @RequestBody ClienteDTO clienteDTO) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        clienteDTO.setId(id);
        clienteService.save(clienteDTO);
        return ResponseEntity.ok(clienteDTO);
    }


    @PostMapping(path = "/registro")
    public ResponseEntity<ClienteDTO> registrarCliente(@RequestBody ClienteDTO clienteDTO){
        if(clienteDTO.getId() != null){
            return ResponseEntity.badRequest().build();
        }
        clienteService.save(clienteDTO);
        return ResponseEntity.ok(clienteDTO);
    }

    @PutMapping(path = "/")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente){
        if(cliente.getId() == null || clienteRepository.existsById(cliente.getId())){
            return ResponseEntity.badRequest().build();
        }
        clienteRepository.save(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping(path = "/{idCliente}")
    public ResponseEntity<Cliente> eliminarCliente(@PathVariable("idCliente") Long idCliente){
        if(idCliente == null || !clienteRepository.existsById(idCliente)){
            return ResponseEntity.badRequest().build();
        }
        clienteRepository.deleteById(idCliente);
        return ResponseEntity.noContent().build();
    }
}