package com.dtb.algamoney.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.dtb.algamoney.api.model.entity.Categoria;
import com.dtb.algamoney.api.model.event.RecursoCriadoEvent;
import com.dtb.algamoney.api.model.repository.CategoriaRepository;

@Service
public class CategoriaService {
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	public Categoria criar(Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		return categoriaSalva;
	}
	public Optional<Categoria> buscarCategoriaId(Long id){
		Optional <Categoria> categoria = categoriaRepository.findById(id);
		if(categoria.isPresent()) {
			return categoria;
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}
}
