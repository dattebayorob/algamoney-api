package com.dtb.algamoney.api.model.repository.lancamento;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.dtb.algamoney.api.model.dto.LancamentoEstatisticaPorCategoria;
import com.dtb.algamoney.api.model.dto.LancamentoEstatisticaPorDia;
import com.dtb.algamoney.api.model.dto.LancamentoResumido;
import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	public List<LancamentoEstatisticaPorCategoria> porCategoria(LocalDate mesReferencia);
	public List<LancamentoEstatisticaPorDia> porDia(LocalDate mesReferencia);
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter,Pageable pageable);
	public Page<LancamentoResumido> resumir(LancamentoFilter lancamentoFilter,Pageable pageable);
}
