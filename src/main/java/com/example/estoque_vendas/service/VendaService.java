package com.example.estoque_vendas.service;

import com.example.estoque_vendas.model.Cliente;
import com.example.estoque_vendas.model.ItemVenda;
import com.example.estoque_vendas.model.Produto;
import com.example.estoque_vendas.model.Venda;
import com.example.estoque_vendas.repository.ClienteRepository;
import com.example.estoque_vendas.repository.ProdutoRepository;
import com.example.estoque_vendas.repository.VendaRepository;
import com.example.estoque_vendas.dto.ItemVendaRequest;
import com.example.estoque_vendas.exception.BadRequestException;
import com.example.estoque_vendas.exception.ResourceNotFoundException; // Certifique-se de importar suas novas exceções

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Venda> findAll() {
        return vendaRepository.findAll();
    }

    public Optional<Venda> findById(Long id) {
        return vendaRepository.findById(id);
    }

    @Transactional
    public Venda registrarVenda(Long clienteId, List<ItemVendaRequest> itensVendaRequest) {
        // 1. Buscar o cliente
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId));

        Venda novaVenda = new Venda();
        novaVenda.setCliente(cliente);
        novaVenda.setDataVenda(LocalDateTime.now());
        novaVenda.setValorTotal(BigDecimal.ZERO); // Inicializa com zero

        // 2. Processar cada item de venda (DTO) e converter para entidade ItemVenda
        for (ItemVendaRequest itemRequest : itensVendaRequest) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + itemRequest.getProdutoId()));

            if (itemRequest.getQuantidade() <= 0) { // Adiciona validação para quantidade
                throw new BadRequestException("A quantidade do produto " + produto.getNome() + " deve ser maior que zero.");
            }

            if (produto.getQuantidadeEstoque() < itemRequest.getQuantidade()) {
                throw new BadRequestException("Estoque insuficiente para o produto: " + produto.getNome() + ". Disponível: " + produto.getQuantidadeEstoque() + ", Solicitado: " + itemRequest.getQuantidade());
            }

            // Diminuir o estoque do produto
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemRequest.getQuantidade());
            produtoRepository.save(produto); // Salva a atualização do estoque

            // Criar a entidade ItemVenda a partir do DTO e do Produto
            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setPrecoUnitario(produto.getPrecoVenda()); // Usa o preço de venda atual do produto

            novaVenda.adicionarItem(item); // Adiciona o item à lista de itens da venda
        }

        // 3. Calcular o valor total da venda
        novaVenda.calcularValorTotal();

        // 4. Salvar a nova venda (que salvará os itensVenda em cascata)
        return vendaRepository.save(novaVenda);
    }

    // Métodos de atualização e deleção para vendas podem ser mais complexos
    // devido à necessidade de reverter estoque, etc. Por enquanto, focaremos na criação.
}