package com.example.estoque_vendas.dto;

import lombok.Data;
import java.util.List;

@Data
public class RegistrarVendaRequest {
    private Long clienteId;
    private List<ItemVendaRequest> itens;
}