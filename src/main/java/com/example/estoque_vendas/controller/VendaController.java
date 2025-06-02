package com.example.estoque_vendas.controller;

import com.example.estoque_vendas.model.Venda;
// import com.example.estoque_vendas.model.ItemVenda; // REMOVA: Isso era um erro, não é para ser usado aqui
import com.example.estoque_vendas.service.VendaService;
import com.example.estoque_vendas.dto.RegistrarVendaRequest; // ADICIONADO: Import do DTO de requisição

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// import java.util.Map; // REMOVA: Não é mais necessário para o payload

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
    // CORREÇÃO: O Spring Boot fará o mapeamento automático do JSON para o DTO RegistrarVendaRequest
    public ResponseEntity<?> registrarVenda(@RequestBody RegistrarVendaRequest request) {
        try {
            // CORREÇÃO: Chamada ao serviço usando os campos do DTO de requisição
            Venda novaVenda = vendaService.registrarVenda(request.getClienteId(), request.getItens());
            return ResponseEntity.status(HttpStatus.CREATED).body(novaVenda);
        } catch (RuntimeException e) {
            // Retorna uma resposta de erro com a mensagem da exceção
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Você pode adicionar endpoints para cancelar vendas, relatórios, etc.
}