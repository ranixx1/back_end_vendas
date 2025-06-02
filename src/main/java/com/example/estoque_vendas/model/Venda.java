package com.example.estoque_vendas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "vendas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Uma venda tem um cliente
    @JoinColumn(name = "cliente_id", nullable = false) // Coluna de chave estrangeira
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDateTime dataVenda;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    // Uma venda tem muitos itens de venda
    // cascade = CascadeType.ALL: Operações (persistir, remover) em Venda afetam ItemVenda
    // orphanRemoval = true: Remove itens de venda órfãos (ex: se remover da lista)
    private List<ItemVenda> itens = new ArrayList<>();

    // Método auxiliar para adicionar itens à venda e manter a consistência bidirecional
    public void adicionarItem(ItemVenda item) {
        itens.add(item);
        item.setVenda(this); // Garante que o item de venda conheça sua venda
    }

    // Método auxiliar para calcular o valor total da venda
    public void calcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(item -> item.getPrecoUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}