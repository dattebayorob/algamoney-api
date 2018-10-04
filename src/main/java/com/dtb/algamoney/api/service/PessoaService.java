package com.dtb.algamoney.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	public Pessoa atualizarPessoa(Long id, Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = buscarPessoa(id);
		pessoa.setId(id);
		return pessoaRepository.save(pessoa);
	}
	
	public void atualizarPessoaAtivo(Long id, Boolean ativo) {
		Optional<Pessoa> pessoaSalva = buscarPessoa(id);
		pessoaSalva.get().setAtivo(ativo);
		pessoaRepository.save(pessoaSalva.get());
	}
	
	private Optional<Pessoa> buscarPessoa(Long id) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
		if(pessoaSalva.isPresent()) {
			return pessoaSalva;
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	public void removerPessoa(Long id) {
		pessoaRepository.deleteById(id);
	}
}
