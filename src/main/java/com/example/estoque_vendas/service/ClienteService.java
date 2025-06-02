package com.example.estoque_vendas.service;


import com.example.estoque_vendas.model.Cliente;
import com.example.estoque_vendas.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired // Injeção de dependência do ClienteRepository
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente save(Cliente cliente) {
        // Exemplo de validação de negócio:
        // if (clienteRepository.findByDocumento(cliente.getDocumento()).isPresent()) {
        //     throw new RuntimeException("Já existe um cliente com este documento!");
        // }
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
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
    }

    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }
}