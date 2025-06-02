package com.example.estoque_vendas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "clientes") // Nome da tabela no banco de dados
@Data // Lombok: gera getters, setters, equals, hashCode, toString
@NoArgsConstructor // Lombok: construtor sem argumentos
@AllArgsConstructor // Lombok: construtor com todos os argumentos
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 14) // Ex: CPF/CNPJ, deve ser único
    private String documento; // CPF ou CNPJ

    @Column(length = 255)
    private String email;

    @Column(length = 20)
    private String telefone;

    // Outros campos como endereço, data de cadastro, etc.
}