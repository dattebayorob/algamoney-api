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
public class PessoaService implements BasicoService<Pessoa, PessoaFilter> {

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	}

	@Override
	public Pessoa adicionar(Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		return pessoaSalva;
	}

	@Override
	public Pessoa atualizar(Pessoa pessoa, Long id) {
		Optional<Pessoa> pessoaSalva = buscarPeloId(id);
		pessoa.setId(id);
		return pessoaRepository.save(pessoa);
	}

	public void atualizarPessoaAtivo(Long id, Boolean ativo) {
		Optional<Pessoa> pessoaSalva = buscarPeloId(id);
		pessoaSalva.get().setAtivo(ativo);
		pessoaRepository.save(pessoaSalva.get());
	}

	@Override
	public Optional<Pessoa> buscarPeloId(Long id) {
		Optional<Pessoa> pessoaSalva = pessoaRepository.findById(id);
		if (pessoaSalva.isPresent()) {
			return pessoaSalva;
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	@Override
	public void removerPeloId(Long id) {
		pessoaRepository.deleteById(id);
	}
}
