package com.example.demo.web.controller;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ws/clientes")
public class ClienteRestController {private static final Logger log =
        LoggerFactory.getLogger(ClienteRestController.class);
    @Autowired
    private ClienteService clienteService;

    // Listar los clientes
    @RequestMapping(method = RequestMethod.GET)
    public List<ClienteDTO> findAll() {

        log.info("ClienteRestController - findAll: Mostramos todos los clientes");

        List<ClienteDTO> listaClientesDTO = clienteService.findAll();
        return listaClientesDTO;
    }

    // Visualizar la informacion de un cliente
    @RequestMapping(method = RequestMethod.GET, path = "/{idCliente}")
    public ClienteDTO findById(@PathVariable("idCliente") Long idCliente) {

        log.info("ClienteRestController - findById: Mostramos la informacion del cliente:" + idCliente);

        // Obtenemos el cliente y lo pasamos al modelo
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);
        clienteDTO = clienteService.findById(clienteDTO);
        return clienteDTO;
    }
}