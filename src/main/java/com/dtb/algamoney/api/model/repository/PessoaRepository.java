package com.dtb.algamoney.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.algamoney.api.model.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
