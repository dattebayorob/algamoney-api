package com.dtb.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.event.RecursoCriadoEvent;
import com.dtb.algamoney.api.model.exception.PessoaInexistenteOuInativaException;
import com.dtb.algamoney.api.model.repository.LancamentoRepository;
import com.dtb.algamoney.api.model.repository.PessoaRepository;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;
import com.dtb.algamoney.api.model.repository.projection.ResumoLancamento;

@Service
public class LancamentoService {
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public Lancamento atualizarLancamento(Lancamento lancamento, Long id) {
		Optional<Lancamento> lancamentoSalvo = buscarLancamentoId(id);
		lancamento.setId(id);
		return lancamentoRepository.save(lancamento);
	}
	
	public Optional<Lancamento> buscarLancamentoId(Long id){
		Optional<Lancamento> lancamento= lancamentoRepository.findById(id);
		if(lancamento.isPresent()) {
			return lancamento;
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}
	public Page<Lancamento> buscarLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){
		Page<Lancamento> lancamentos = lancamentoRepository.filtrar(lancamentoFilter, pageable);
		return lancamentos;
	}
	public Page<ResumoLancamento> resumirLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){
		Page<ResumoLancamento> lancamentos = lancamentoRepository.resumir(lancamentoFilter, pageable);
		return lancamentos;
	}
	
	public Lancamento adicionarLancamento(Lancamento lancamento,HttpServletResponse response) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getId());
		if(!pessoa.isPresent() || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		} 
		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));
		return lancamentoSalvo;
	}

	public void removerLancamento(Long id) {
		lancamentoRepository.deleteById(id);
	}
}
