package com.example.estoque_vendas.repository;

import com.example.estoque_vendas.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; 

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // JpaRepository<Tipo da Entidade, Tipo da Chave Primária da Entidade>

    // Você pode adicionar métodos personalizados aqui, se precisar de consultas específicas.
    // Por exemplo, para encontrar um cliente pelo documento:
    Optional<Cliente> findByDocumento(String documento);
}