package com.example.estoque_vendas.repository;

import com.example.estoque_vendas.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, mas boa prática para indicar que é um componente de repositório
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // JpaRepository<Tipo da Entidade, Tipo da Chave Primária>

    // O Spring Data JPA já fornece métodos como save(), findById(), findAll(), deleteById(), etc.
    // Você pode adicionar métodos de consulta personalizados aqui se precisar, por exemplo:
    // List<Produto> findByNomeContainingIgnoreCase(String nome);
}