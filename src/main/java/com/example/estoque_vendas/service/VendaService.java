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
import com.example.estoque_vendas.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VendaService {

    private static final Logger logger = LoggerFactory.getLogger(VendaService.class);

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Venda> findAll() {
        logger.info("Buscando todas as vendas.");
        return vendaRepository.findAll();
    }

    public Optional<Venda> findById(Long id) {
        logger.info("Buscando venda com ID: {}", id);
        return vendaRepository.findById(id);
    }

    @Transactional
    public Venda registrarVenda(Long clienteId, List<ItemVendaRequest> itensVendaRequest) {
        logger.info("Iniciando registro de venda para o cliente ID: {} com {} itens.", clienteId, itensVendaRequest.size());

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> {
                    logger.error("Cliente não encontrado com ID: {}", clienteId);
                    return new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId);
                });
        logger.debug("Cliente encontrado: {}", cliente.getNome());

        Venda novaVenda = new Venda();
        novaVenda.setCliente(cliente);
        novaVenda.setDataVenda(LocalDateTime.now());
        novaVenda.setValorTotal(BigDecimal.ZERO);

        for (ItemVendaRequest itemRequest : itensVendaRequest) {
            Produto produto = produtoRepository.findById(itemRequest.getProdutoId())
                    .orElseThrow(() -> {
                        logger.error("Produto não encontrado com ID: {} durante o registro da venda.", itemRequest.getProdutoId());
                        return new ResourceNotFoundException("Produto não encontrado com ID: " + itemRequest.getProdutoId());
                    });
            logger.debug("Processando item: Produto {} (ID: {}), Quantidade: {}", produto.getNome(), produto.getId(), itemRequest.getQuantidade());

            if (itemRequest.getQuantidade() <= 0) {
                logger.warn("Tentativa de vender produto {} com quantidade inválida: {}", produto.getNome(), itemRequest.getQuantidade());
                throw new BadRequestException("A quantidade do produto " + produto.getNome() + " deve ser maior que zero.");
            }

            if (produto.getQuantidadeEstoque() < itemRequest.getQuantidade()) {
                logger.warn("Estoque insuficiente para o produto: {}. Disponível: {}, Solicitado: {}", produto.getNome(), produto.getQuantidadeEstoque(), itemRequest.getQuantidade());
                throw new BadRequestException("Estoque insuficiente para o produto: " + produto.getNome() + ". Disponível: " + produto.getQuantidadeEstoque() + ", Solicitado: " + itemRequest.getQuantidade());
            }

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - itemRequest.getQuantidade());
            produtoRepository.save(produto);
            logger.info("Estoque do produto {} (ID: {}) atualizado para: {}", produto.getNome(), produto.getId(), produto.getQuantidadeEstoque());

            ItemVenda item = new ItemVenda();
            item.setProduto(produto);
            item.setQuantidade(itemRequest.getQuantidade());
            item.setPrecoUnitario(produto.getPrecoVenda());

            novaVenda.adicionarItem(item);
        }

        novaVenda.calcularValorTotal();
        logger.info("Valor total da venda calculado: {}", novaVenda.getValorTotal());

        Venda vendaSalva = vendaRepository.save(novaVenda);
        logger.info("Venda registrada com sucesso! ID da Venda: {}", vendaSalva.getId());
        return vendaSalva;
    }

    @Transactional
    public void cancelarVenda(Long vendaId) {
        logger.info("Iniciando cancelamento da venda com ID: {}", vendaId);

        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> {
                    logger.error("Venda não encontrada para cancelamento com ID: {}", vendaId);
                    return new ResourceNotFoundException("Venda não encontrada com ID: " + vendaId);
                });

        // 1. Devolver os itens da venda para o estoque
        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();
            Integer quantidadeDevolvida = item.getQuantidade();

            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidadeDevolvida);
            produtoRepository.save(produto);
            logger.info("Estoque do produto {} (ID: {}) atualizado para {} após cancelamento da venda {}.",
                        produto.getNome(), produto.getId(), produto.getQuantidadeEstoque(), vendaId);
        }

        // 2. Deletar a venda (os itens de venda serão deletados em cascata devido a CascadeType.ALL)
        vendaRepository.delete(venda);
        logger.info("Venda com ID: {} cancelada e removida com sucesso.", vendaId);
    }
}