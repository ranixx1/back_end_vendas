package com.example.estoque_vendas.controller;

import com.example.estoque_vendas.model.Produto;
import com.example.estoque_vendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<Page<Produto>> getAllProdutos(Pageable pageable) {
        Page<Produto> produtosPage = produtoService.findAll(pageable);
        return ResponseEntity.ok(produtosPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> ResponseEntity.ok(produto))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto savedProduto = produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto updatedProduto = produtoService.update(id, produto);
        return ResponseEntity.ok(updatedProduto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}