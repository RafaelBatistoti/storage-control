package com.projeto.estoque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.estoque.dto.Pessoa;
import com.projeto.estoque.interfaces.IPessoaRepo;
import com.projeto.estoque.interfaces.IPessoaService;

@Service
public class PessoaServ implements IPessoaService {

	@Autowired
	private IPessoaRepo pessoaRepo;

	@Override
	public List<Pessoa> getAllPessoa() {
		return pessoaRepo.findAll();
	}

	@Override
	public void savePessoa(Pessoa pessoa) {
		this.pessoaRepo.save(pessoa);

	}

	@Override
	public Pessoa getById(String code) {
		Optional<Pessoa> optional = pessoaRepo.findById(code);
		Pessoa employee = null;
		if (optional.isPresent())
			employee = optional.get();
		else
			throw new RuntimeException("Employee not found for id");
		return employee;
	}

}
