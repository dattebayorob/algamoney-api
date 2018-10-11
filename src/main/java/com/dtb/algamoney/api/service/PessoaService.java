package com.dtb.algamoney.api.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.event.RecursoCriadoEvent;
import com.dtb.algamoney.api.model.repository.PessoaRepository;
import com.dtb.algamoney.api.model.repository.filter.PessoaFilter;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;
	
	public Page<Pessoa> buscarPessoas(PessoaFilter pessoaFilter, Pageable pageable){
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	} 
	public Pessoa adicionarPessoa(Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		return pessoaSalva;
	}
	public Pessoa atualizarPessoa(Long id, Pessoa pessoa) {
		Optional<Pessoa> pessoaSalva = buscaPessoaId(id);
		pessoa.setId(id);
		return pessoaRepository.save(pessoa);
	}
	
	public void atualizarPessoaAtivo(Long id, Boolean ativo) {
		Optional<Pessoa> pessoaSalva = buscaPessoaId(id);
		pessoaSalva.get().setAtivo(ativo);
		pessoaRepository.save(pessoaSalva.get());
	}
	
	public Optional<Pessoa> buscaPessoaId(Long id) {
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
