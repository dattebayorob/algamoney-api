package com.dtb.algamoney.api.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
import com.dtb.algamoney.api.model.event.RecursoCriadoEvent;
import com.dtb.algamoney.api.model.repository.PessoaRepository;
import com.dtb.algamoney.api.model.repository.filter.PessoaFilter;
import com.dtb.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private PessoaService pessoaService;
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public Page<Pessoa> listar(PessoaFilter pessoaFilter, Pageable pageable){
		return pessoaRepository.filtrar(pessoaFilter, pageable);
	} 
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA') and #oauth2.hasScope('read')")
	public ResponseEntity<?> buscaPessoaId(@PathVariable Long id){
		Optional<Pessoa> pessoaById = pessoaRepository.findById(id);
		if(pessoaById.isPresent()) {
			return ResponseEntity.ok(pessoaById);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> adicionarPessoa(@Valid @RequestBody Pessoa pessoa,
			HttpServletResponse response){
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA') and #oauth2.hasScope('write')")
	public void removerPessoa(@PathVariable Long id){
		pessoaService.removerPessoa(id);
	}
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	public ResponseEntity<Pessoa> atualizarPessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.atualizarPessoa(id, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	@PutMapping("{id}/ativo")
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA') and #oauth2.hasScope('write')")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPessoaAtivo(@PathVariable Long id, @RequestBody Boolean ativo) {
		pessoaService.atualizarPessoaAtivo(id, ativo);
	}
}
