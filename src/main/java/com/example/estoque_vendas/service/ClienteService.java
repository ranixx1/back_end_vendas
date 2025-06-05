package com.example.estoque_vendas.service;

import com.example.estoque_vendas.model.Cliente;
import com.example.estoque_vendas.repository.ClienteRepository;
import com.example.estoque_vendas.exception.ResourceNotFoundException; // Importar

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente update(Long id, Cliente clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(clienteExistente -> {
                    clienteExistente.setNome(clienteAtualizado.getNome());
                    clienteExistente.setDocumento(clienteAtualizado.getDocumento());
                    clienteExistente.setEmail(clienteAtualizado.getEmail());
                    clienteExistente.setTelefone(clienteAtualizado.getTelefone());
                    // Atualize outros campos conforme necessário
                    return clienteRepository.save(clienteExistente);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id)); // Usando ResourceNotFoundException
    }

    public void deleteById(Long id) {
        //adicionando verificação se o cliente existe antes de deletar
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}