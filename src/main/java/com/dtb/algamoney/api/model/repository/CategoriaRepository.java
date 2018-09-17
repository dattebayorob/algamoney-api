package com.dtb.algamoney.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.algamoney.api.model.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
	
}
