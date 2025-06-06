package com.example.estoque_vendas.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;  
import java.util.List;

@Data
public class RegistrarVendaRequest {
    @NotNull(message = "O ID do cliente não pode ser nulo.")
    private Long clienteId;

    @NotNull(message = "A lista de itens da venda não pode ser nula.")
    @Size(min = 1, message = "A venda deve conter pelo menos um item.")
    private List<ItemVendaRequest> itens;
}