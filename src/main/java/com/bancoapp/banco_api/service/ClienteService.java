package com.bancoapp.banco_api.service;

import com.bancoapp.banco_api.model.Cliente;
import com.bancoapp.banco_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente crearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setDireccion(clienteActualizado.getDireccion());
                    cliente.setTelefono(clienteActualizado.getTelefono());
                    cliente.setContrasena(clienteActualizado.getContrasena());
                    cliente.setEstado(clienteActualizado.isEstado());
                    return clienteRepository.save(cliente);
                })
                .orElse(null);
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
