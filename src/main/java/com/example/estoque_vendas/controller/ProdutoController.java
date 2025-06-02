package com.example.estoque_vendas.controller;


import com.example.estoque_vendas.model.Produto;
import com.example.estoque_vendas.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/produtos") // Define o caminho base para todos os endpoints neste controlador
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping // GET /api/produtos - Retorna todos os produtos
    public ResponseEntity<List<Produto>> getAllProdutos() {
        List<Produto> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos); // Retorna 200 OK com a lista de produtos
    }

    @GetMapping("/{id}") // GET /api/produtos/{id} - Retorna um produto por ID
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(produto -> ResponseEntity.ok(produto)) // Retorna 200 OK com o produto
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 Not Found se não encontrar
    }

    @PostMapping // POST /api/produtos - Cria um novo produto
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        Produto savedProduto = produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduto); // Retorna 201 CREATED
    }

    @PutMapping("/{id}") // PUT /api/produtos/{id} - Atualiza um produto existente
    public ResponseEntity<Produto> updateProduto(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            Produto updatedProduto = produtoService.update(id, produto);
            return ResponseEntity.ok(updatedProduto); // Retorna 200 OK com o produto atualizado
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found se não encontrar
        }
    }

    @DeleteMapping("/{id}") // DELETE /api/produtos/{id} - Deleta um produto por ID
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso sem conteúdo)
    }
}