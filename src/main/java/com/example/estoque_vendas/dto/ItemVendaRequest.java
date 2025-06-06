package com.example.estoque_vendas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaRequest {
    @NotNull(message = "O ID do produto não pode ser nulo.")
    private Long produtoId;
    @NotNull(message = "A quantidade não pode ser nula.")
    @Min(value = 1, message = "A quantidade deve ser maior que zero.")
    private Integer quantidade;
}