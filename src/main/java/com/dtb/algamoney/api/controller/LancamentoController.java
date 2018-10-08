package com.dtb.algamoney.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;
import com.dtb.algamoney.api.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
	
	@Autowired
	private LancamentoService lancamentoService;
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public Page<Lancamento> buscaLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoService.buscarLancamentos(lancamentoFilter, pageable);
	}
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscaLancamentoId(@PathVariable Long id ) {
		Optional<Lancamento> lancamento = lancamentoService.buscarLancamentoId(id);
		return ResponseEntity.ok(lancamento);
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasSCope('write')")
	public ResponseEntity<Lancamento> atualizarLancamento(@Valid @RequestBody Lancamento lancamento, @PathVariable Long id){
		Lancamento lancamentoSalvo = lancamentoService.atualizarLancamento(lancamento, id);
		return ResponseEntity.ok(lancamentoSalvo);
	}
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
	public ResponseEntity<Lancamento> adicionarLancamento(@Valid @RequestBody Lancamento lancamento,
			HttpServletResponse response){
		Lancamento lancamentoSalvo = lancamentoService.adicionarLancamento(lancamento,response);
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
	public void removerLancamento(@PathVariable Long id){
		lancamentoService.removerLancamento(id);
	}
}
