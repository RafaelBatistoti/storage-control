package com.projeto.estoque.interfaces;

import java.util.List;

import com.projeto.estoque.dto.Pessoa;

public interface IPessoaService {

	List<Pessoa> getAllPessoa();

	void savePessoa(Pessoa item);

	Pessoa getById(String code);
}
