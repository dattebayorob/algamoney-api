package com.dtb.algamoney.api.model.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter,Pageable pageable);
}
