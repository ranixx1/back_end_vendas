package com.example.estoque_vendas.repository;

import com.example.estoque_vendas.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {
    // Métodos CRUD básicos já vêm do JpaRepository
}