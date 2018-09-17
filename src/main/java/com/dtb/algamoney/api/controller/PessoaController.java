package com.dtb.algamoney.api.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.repository.PessoaRepository;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	} 
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaPessoaId(@PathVariable Long id){
		Optional<Pessoa> pessoaById = pessoaRepository.findById(id);
		if(pessoaById.isPresent()) {
			return ResponseEntity.ok(pessoaById);
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	@PostMapping
	public ResponseEntity<Pessoa> adicionarPessoa(@Valid @RequestBody Pessoa pessoa,
			HttpServletResponse response){
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(pessoaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(pessoaSalva);
	}
}
