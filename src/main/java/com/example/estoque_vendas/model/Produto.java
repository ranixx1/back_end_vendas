package com.example.estoque_vendas.model;

import jakarta.persistence.*; // Use jakarta.persistence para Spring Boot 3+
import lombok.Data; // Se estiver usando Lombok
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Entity // Indica que esta classe é uma entidade JPA e será mapeada para uma tabela
@Table(name = "produtos") // Opcional, define o nome da tabela no DB
@Data // Anotação Lombok para getters, setters, toString, equals, hashCode
@NoArgsConstructor // Lombok: construtor sem argumentos
@AllArgsConstructor // Lombok: construtor com todos os argumentos
public class Produto {

    @Id // Indica que este campo é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Estratégia de geração de ID (auto-incremento)
    private Long id;

    @Column(nullable = false, length = 255) // Define propriedades da coluna (não nula, tamanho máximo)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @Column(nullable = false, precision = 10, scale = 2) // Precisão total de 10 dígitos, 2 decimais
    private BigDecimal precoCusto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precoVenda;

    /*
    public Produto() {} // Construtor padrão

    public Produto(Long id, String nome, String descricao, Integer quantidadeEstoque, BigDecimal precoCusto, BigDecimal precoVenda) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
    }

    // Getters e Setters
    // ... (código omitido para brevidade)
    */
}