package com.dtb.algamoney.api.model.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;
import com.dtb.algamoney.api.model.repository.projection.ResumoLancamento;

public interface LancamentoRepositoryQuery {
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter,Pageable pageable);
	public Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter,Pageable pageable);
}
