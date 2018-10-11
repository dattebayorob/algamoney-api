package com.dtb.algamoney.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dtb.algamoney.api.model.entity.Categoria;
import com.dtb.algamoney.api.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;
	private static final String ROLE_PESQUISAR_CATEGORIA = "hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')";
	private static final String ROLE_CADASTRAR_CATEGORIA = "hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')";

	
	@GetMapping
	@PreAuthorize(ROLE_PESQUISAR_CATEGORIA)
	public List<Categoria> listar(){
		return categoriaService.listar();
		
	} 
	@PostMapping
	@PreAuthorize(ROLE_CADASTRAR_CATEGORIA)
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {		
		Categoria categoriaSalva = categoriaService.adicionar(categoria, response);
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_PESQUISAR_CATEGORIA)
	public ResponseEntity<?> buscaCodigo(@PathVariable Long id, HttpServletResponse response) {
		Optional<Categoria> categoria = categoriaService.buscarPeloId(id);
		return ResponseEntity.ok(categoria);
	}
}
