package com.projeto.estoque.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.estoque.dto.Pessoa;

@Repository
public interface IPessoaRepo  extends JpaRepository<Pessoa, String> {}
