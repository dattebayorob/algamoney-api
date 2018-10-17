package com.dtb.algamoney.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.algamoney.api.model.dto.LancamentoEstaticaCategoria;
import com.dtb.algamoney.api.model.dto.LancamentoEstaticaPorDia;
import com.dtb.algamoney.api.model.dto.LancamentoResumido;
import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;
import com.dtb.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	@Autowired
	private LancamentoService lancamentoService;
	
	private static final String ROLE_PESQUISAR_LANCAMENTO = "hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')";
	private static final String ROLE_CADASTRAR_LANCAMENTO = "hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')";
	private static final String ROLE_REMOVER_LANCAMENTO = "hasAuthority('ROLE_REMOVER_LANCAMENTOR') and #oauth2.hasScope('write')";
	
	
	@GetMapping
	@PreAuthorize(ROLE_PESQUISAR_LANCAMENTO)
	public Page<Lancamento> buscaLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoService.filtrar(lancamentoFilter, pageable);
	}
	@GetMapping(params = "resumo")
	@PreAuthorize(ROLE_PESQUISAR_LANCAMENTO)
	public Page<LancamentoResumido> resumirLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoService.resumirLancamentos(lancamentoFilter, pageable);
	}
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_PESQUISAR_LANCAMENTO)
	public ResponseEntity<?> buscaLancamentoId(@PathVariable Long id ) {
		try {
			Optional<Lancamento> lancamento = lancamentoService.buscarPeloId(id);
			return ResponseEntity.ok(lancamento);
		}catch (EmptyResultDataAccessException e) {
			return ResponseEntity.notFound().build();
		}
	}
	@GetMapping("/estatisticas/por-categoria")
	@PreAuthorize(ROLE_PESQUISAR_LANCAMENTO)
	public List<LancamentoEstaticaCategoria> porCategoria(){
		return this.lancamentoService.porCategoria();
	}
	
	@GetMapping("/estatisticas/por-dia")
	@PreAuthorize(ROLE_PESQUISAR_LANCAMENTO)
	public List<LancamentoEstaticaPorDia> porDia(){
		return this.lancamentoService.porDia();
	}
	@PutMapping("/{id}")
	@PreAuthorize(ROLE_CADASTRAR_LANCAMENTO)
	public ResponseEntity<Lancamento> atualizarLancamento(@Valid @RequestBody Lancamento lancamento, @PathVariable Long id){
		Lancamento lancamentoSalvo = lancamentoService.atualizar(lancamento, id);
		return ResponseEntity.ok(lancamentoSalvo);
	}
	@PostMapping
	@PreAuthorize(ROLE_CADASTRAR_LANCAMENTO)
	public ResponseEntity<Lancamento> adicionarLancamento(@Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response){
		Lancamento lancamentoSalvo = lancamentoService.adicionar(lancamento,response);
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize(ROLE_REMOVER_LANCAMENTO)
	public void removerLancamento(@PathVariable Long id){
		lancamentoService.removerPeloId(id);
	}
}
