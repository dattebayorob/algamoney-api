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

import com.dtb.algamoney.api.model.entity.Categoria;
import com.dtb.algamoney.api.model.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	} 
	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {		
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
			.buildAndExpand(categoriaSalva.getId()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		return ResponseEntity.created(uri).body(categoriaSalva);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> buscaCodigo(@PathVariable Long id, HttpServletResponse response) {
		Optional<Categoria> categoriaById = categoriaRepository.findById(id);
		if (categoriaById.isPresent()) {
			return ResponseEntity.ok(categoriaById);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
}
