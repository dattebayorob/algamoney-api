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

import com.dtb.algamoney.api.model.entity.Categoria;
import com.dtb.algamoney.api.model.event.RecursoCriadoEvent;
import com.dtb.algamoney.api.model.repository.CategoriaRepository;
import com.dtb.algamoney.api.model.repository.filter.CategoriaFilter;

@Service
public class CategoriaService implements BasicoService<Categoria, CategoriaFilter>{
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ApplicationEventPublisher publisher;
	
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	@Override
	public Categoria adicionar(Categoria categoria, HttpServletResponse response){
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));
		return categoriaSalva;
	}
	@Override
	public Optional<Categoria> buscarPeloId(Long id){
		Optional <Categoria> categoria = categoriaRepository.findById(id);
		if(categoria.isPresent()) {
			return categoria;
		}else {
			throw new EmptyResultDataAccessException(1);
		}
	}

	@Override
	public Categoria atualizar(Categoria t, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removerPeloId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Categoria> filtrar(CategoriaFilter filter, Pageable pageAble) {
		// TODO Auto-generated method stub
		return null;
	}
}
