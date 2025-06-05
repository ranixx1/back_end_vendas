package com.example.estoque_vendas.service;

import com.example.estoque_vendas.model.Produto;
import com.example.estoque_vendas.repository.ProdutoRepository;
import com.example.estoque_vendas.exception.ResourceNotFoundException; // Importar

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    public Optional<Produto> findById(Long id) {
        return produtoRepository.findById(id);
    }

    public Produto save(Produto produto) {
        // Aqui você pode adicionar validações de negócio antes de salvar
        return produtoRepository.save(produto);
    }

    public Produto update(Long id, Produto produtoAtualizado) {
        return produtoRepository.findById(id)
                .map(produtoExistente -> {
                    produtoExistente.setNome(produtoAtualizado.getNome());
                    produtoExistente.setDescricao(produtoAtualizado.getDescricao());
                    produtoExistente.setQuantidadeEstoque(produtoAtualizado.getQuantidadeEstoque());
                    produtoExistente.setPrecoCusto(produtoAtualizado.getPrecoCusto());
                    produtoExistente.setPrecoVenda(produtoAtualizado.getPrecoVenda());
                    // Atualize outros campos conforme necessário
                    return produtoRepository.save(produtoExistente);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id)); // Usando ResourceNotFoundException
    }

    public void deleteById(Long id) {
        // Opcional: Adicionar verificação se o produto existe antes de deletar
        if (!produtoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deleteById(id);
    }
}