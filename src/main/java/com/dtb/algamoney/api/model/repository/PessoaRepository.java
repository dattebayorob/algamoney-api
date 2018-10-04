package com.dtb.algamoney.api.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.repository.pessoa.PessoaRepositoryQuery;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>,PessoaRepositoryQuery{

}
