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

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.repository.filter.PessoaFilter;
import com.dtb.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaService pessoaService;
	
	private static final String ROLE_PESQUISAR_PESSOA = "hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')";
	private static final String ROLE_CADASTRAR_PESSOA = "hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')";
	private static final String ROLE_REMOVER_PESSOA = "hasAuthority('ROLE_REMOVER_PESSOAR_PESSOA') and #oauth2.hasScope('write')";
	
	@GetMapping
	@PreAuthorize(ROLE_PESQUISAR_PESSOA)
	public Page<Pessoa> buscarPessoas(PessoaFilter pessoaFilter, Pageable pageable){
		return pessoaService.buscarPessoas(pessoaFilter, pageable);
	} 
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_PESQUISAR_PESSOA)
	public ResponseEntity<?> buscaPessoaId(@PathVariable Long id){
		Optional<Pessoa> pessoa = pessoaService.buscaPessoaId(id);
		return ResponseEntity.ok(pessoa);
		
	}
	@PostMapping
	@PreAuthorize(ROLE_CADASTRAR_PESSOA)
	public ResponseEntity<Pessoa> adicionarPessoa(@Valid @RequestBody Pessoa pessoa,
			HttpServletResponse response){
		Pessoa pessoaSalva = pessoaService.adicionarPessoa(pessoa,response);
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize(ROLE_REMOVER_PESSOA)
	public void removerPessoa(@PathVariable Long id){
		pessoaService.removerPessoa(id);
	}
	@PutMapping("/{id}")
	@PreAuthorize(ROLE_CADASTRAR_PESSOA)
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizarPessoa(id, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	@PutMapping("{id}/ativo")
	@PreAuthorize(ROLE_CADASTRAR_PESSOA)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPessoaAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		pessoaService.atualizarPessoaAtivo(id, ativo);
	}
}
