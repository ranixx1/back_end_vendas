package com.example.estoque_vendas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaRequest {
    private Long produtoId;
    private Integer quantidade;
    // O preço unitário será pego do Produto no Service
}