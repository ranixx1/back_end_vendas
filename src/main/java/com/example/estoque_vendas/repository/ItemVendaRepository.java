package com.example.estoque_vendas.repository;

import com.example.estoque_vendas.model.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    // Métodos CRUD básicos já vêm do JpaRepository
}