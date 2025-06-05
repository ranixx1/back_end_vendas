package com.example.estoque_vendas.controller;

import com.example.estoque_vendas.model.Venda;
import com.example.estoque_vendas.service.VendaService;
import com.example.estoque_vendas.dto.RegistrarVendaRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<Venda>> getAllVendas() {
        List<Venda> vendas = vendaService.findAll();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> getVendaById(@PathVariable Long id) {
        return vendaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping // Endpoint para registrar uma nova venda
    public ResponseEntity<Venda> registrarVenda(@RequestBody RegistrarVendaRequest request) {
        Venda novaVenda = vendaService.registrarVenda(request.getClienteId(), request.getItens());
        return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
    }

    // Você pode adicionar endpoints para cancelar vendas, relatórios, etc.
}