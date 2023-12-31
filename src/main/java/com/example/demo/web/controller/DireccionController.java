package com.example.demo.web.controller;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.DireccionDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.DireccionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DireccionController {
    private static final Logger log = LoggerFactory.getLogger(DireccionController.class);
    @Autowired
    private DireccionService direccionService;
    @Autowired
    private ClienteService clienteService;
    @GetMapping("/clientes/{idCliente}/direcciones")
    public ModelAndView findAllByCliente(@PathVariable Long idCliente) {
        log.info("DireccionController - findAllByCliente: Listamos las direcciones del cliente: " + idCliente);
        // Obtenemos el cliente para luego poner sus datos en la pantalla
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);
        clienteDTO = clienteService.findById(clienteDTO);
        // Obtenemos la lista de direcciones del cliente
        List<DireccionDTO> listaDireccionesDTO =
                direccionService.findAllByCliente(clienteDTO);
        // Pasamos los datos al modelo
        ModelAndView mav = new ModelAndView("direcciones");
        mav.addObject("listaDireccionesDTO", listaDireccionesDTO);
        mav.addObject("clienteDTO", clienteDTO);
        // retornamos el ModelAndView
        return mav;
    }
    @GetMapping("/clientes/{idCliente}/direcciones/add")
    public ModelAndView add(@PathVariable Long idCliente) {
        log.info("DireccionController - add: Anyadimos una nueva direccion al cliente: " +
                idCliente);
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);
        clienteDTO = clienteService.findById(clienteDTO);
        // Pasamos los datos al modelo
        ModelAndView mav = new ModelAndView("direccionesform");
        mav.addObject("direccionDTO", new DireccionDTO());
        mav.addObject("clienteDTO", clienteDTO);
        // retornamos el ModelAndView
        return mav;
    }

    @PostMapping("/clientes/{idCliente}/direcciones/save")
    public ModelAndView save(@PathVariable Long idCliente,
                             @ModelAttribute("direccionDTO") DireccionDTO direccionDTO) {
        log.info("DireccionController - save: Almacenamos la direccion " +
                direccionDTO.toString());
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);
        clienteDTO = clienteService.findById(clienteDTO);
        // Anyadimos al cliente la direccion. Puede haber varias direcciones ya en el
        // clienteDTO
        clienteDTO.getListaDireccionesDTO().add(direccionDTO);
        // Anyadimos a la direccion el cliente. En este momento solo habra un clienteDTO
        direccionDTO.getListaClientesDTO().add(clienteDTO);
        direccionService.save(direccionDTO);
        // Redireccionamos para volver a invocar el metodo que escucha /clientes
        ModelAndView mav = new ModelAndView("redirect:/clientes/{idCliente}/direcciones");
        return mav;
    }
}