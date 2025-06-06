package com.example.estoque_vendas.controller;

import com.example.estoque_vendas.model.Venda;
import com.example.estoque_vendas.service.VendaService;
import com.example.estoque_vendas.dto.RegistrarVendaRequest;

import jakarta.validation.Valid; // Garanta que esta importação está presente!

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger; // Importar
import org.slf4j.LoggerFactory; // Importar

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private static final Logger logger = LoggerFactory.getLogger(VendaController.class); // Seu logger

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<Venda>> getAllVendas() {
        logger.info("Requisição para obter todas as vendas recebida.");
        List<Venda> vendas = vendaService.findAll();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id) {
        logger.info("Requisição para obter venda por ID: {}", id);
        return vendaService.findById(id)
                .map(venda -> {
                    logger.info("Venda com ID: {} encontrada.", id);
                    return ResponseEntity.ok(venda);
                })
                .orElseGet(() -> {
                    logger.warn("Venda com ID: {} não encontrada.", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping // Endpoint para registrar uma nova venda
    // ADICIONE A ANOTAÇÃO @Valid AQUI!
    // REMOVA O BLOCO TRY-CATCH, o GlobalExceptionHandler cuidará dos erros.
    public ResponseEntity<Venda> registrarVenda(@Valid @RequestBody RegistrarVendaRequest request) {
        logger.info("Requisição para registrar nova venda para cliente ID: {}", request.getClienteId());
        Venda novaVenda = vendaService.registrarVenda(request.getClienteId(), request.getItens());
        logger.info("Venda registrada com sucesso. ID da Venda: {}", novaVenda.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    @DeleteMapping("/{id}") // Endpoint para cancelar venda
    public ResponseEntity<Void> cancelarVenda(@PathVariable Long id) {
        logger.info("Requisição para cancelar venda com ID: {}", id);
        vendaService.cancelarVenda(id);
        logger.info("Venda com ID: {} cancelada com sucesso.", id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }
}