package com.example.demo.service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.DireccionDTO;

import java.util.List;

public interface DireccionService {
    public List<DireccionDTO> findAllByCliente(ClienteDTO clienteDTO);
    public void save(DireccionDTO direccionDTO);
}
