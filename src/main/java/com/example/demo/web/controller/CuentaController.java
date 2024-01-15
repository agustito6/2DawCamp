package com.example.demo.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.CuentaDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.CuentaService;

@Controller
public class CuentaController {
    private static final Logger log = LoggerFactory.getLogger(CuentaController.class);
    @Autowired
    private CuentaService cuentaService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes/{idCliente}/cuentas")
    public ModelAndView findAllByCliente(@PathVariable Long idCliente) {
        log.info("ClienteController - findAll: Mostramos todos los clientes");
        // Obtenemos el cliente
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(idCliente);
        clienteDTO = clienteService.findById(clienteDTO);
        // Obtenemos la lista de cuentas
        List<CuentaDTO> listaCuentasDTO = cuentaService.findAllByCliente(clienteDTO);
        ModelAndView mav = new ModelAndView("cuentas");
        mav.addObject("clienteDTO", clienteDTO);
        mav.addObject("listaCuentasDTO", listaCuentasDTO);
        return mav;
    }

    // Borrar una cuenta
    @GetMapping("/clientes/{idCliente}/cuentas/delete/{idCuenta}")
    public ModelAndView delete(@PathVariable("idCliente") Long idCliente,
                               @PathVariable("idCuenta") Long idCuenta) {

        log.info("CuentaController - delete: Usamos el metodo 1 para borrar la cuenta:" +
                idCuenta);

        // Creamos la cuentaDTO
        CuentaDTO cuentaDTO = new CuentaDTO();
        cuentaDTO.setId(idCuenta);
        // Invocamos al metodo de borrar
        cuentaService.delete(cuentaDTO);

        // Redireccionamos para volver a invocar al metodo que escucha /clientes
        ModelAndView mav = new ModelAndView("redirect:/clientes/{idCliente}/cuentas");

        return mav;
    }
}