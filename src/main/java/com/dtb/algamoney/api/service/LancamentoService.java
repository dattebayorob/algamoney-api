package com.dtb.algamoney.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.algamoney.api.model.dto.LancamentoEstatisticaPorCategoria;
import com.dtb.algamoney.api.model.dto.LancamentoEstatisticaPorDia;
import com.dtb.algamoney.api.model.dto.LancamentoResumido;
import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.event.RecursoCriadoEvent;
import com.dtb.algamoney.api.model.exception.PessoaInexistenteOuInativaException;
import com.dtb.algamoney.api.model.repository.LancamentoRepository;
import com.dtb.algamoney.api.model.repository.PessoaRepository;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;

@Service
public class LancamentoService implements BasicoService<Lancamento, LancamentoFilter> {
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	public Lancamento atualizar(Lancamento lancamento, Long id) {
		Optional<Lancamento> lancamentoSalvo = buscarPeloId(id);
		lancamento.setId(id);
		return lancamentoRepository.save(lancamento);
	}

	@Override
	public Optional<Lancamento> buscarPeloId(Long id) {
		Optional<Lancamento> lancamento = lancamentoRepository.findById(id);
		if (lancamento.isPresent()) {
			return lancamento;
		} else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		Page<Lancamento> lancamentos = lancamentoRepository.filtrar(lancamentoFilter, pageable);
		return lancamentos;
	}

	public Page<LancamentoResumido> resumirLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable) {
		Page<LancamentoResumido> lancamentos = lancamentoRepository.resumir(lancamentoFilter, pageable);
		return lancamentos;
	}

	@Override
	public Lancamento adicionar(Lancamento lancamento, HttpServletResponse response) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		if (!pessoa.isPresent() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
		return lancamentoSalvo;
	}

	@Override
	public void removerPeloId(Long id) {
		lancamentoRepository.deleteById(id);
	}
	
	public List<LancamentoEstatisticaPorCategoria> porCategoria(){
		return this.lancamentoRepository.porCategoria(LocalDate.now());
	}

	public List<LancamentoEstatisticaPorDia> porDia(){
		return this.lancamentoRepository.porDia(LocalDate.now());
	}
}
