package com.dtb.algamoney.api.model.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.dtb.algamoney.api.model.dto.LancamentoEstatisticaPorCategoria;
import com.dtb.algamoney.api.model.dto.LancamentoEstatisticaPorDia;
import com.dtb.algamoney.api.model.dto.LancamentoResumido;
import com.dtb.algamoney.api.model.entity.Lancamento;
import com.dtb.algamoney.api.model.repository.filter.LancamentoFilter;
import com.dtb.algamoney.api.model.repository.utils.Paginador;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery{
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		//adicionarRestricoesDePaginacao(query,pageable);
		Paginador<LancamentoFilter> paginador = new Paginador<>();
		paginador.adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(),pageable,
				paginador.total(lancamentoFilter, manager, predicates));//totalDeLancamentos(lancamentoFilter));
	}

	@Override
	public Page<LancamentoResumido> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoResumido> criteria = builder.createQuery(LancamentoResumido.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		criteria.select(builder.construct(LancamentoResumido.class, 
				root.get("id"),
				root.get("descricao"),
				root.get("dataVencimento"),
				root.get("dataPagamento"),
				root.get("valor"),
				root.get("tipo"),
				root.get("categoria").get("nome"),
				root.get("pessoa").get("nome")
				));
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<LancamentoResumido> query = manager.createQuery(criteria);
		Paginador<LancamentoFilter> paginador = new Paginador<>();
		paginador.adicionarRestricoesDePaginacao(query, pageable);
		return new PageImpl<>(query.getResultList(),pageable,
				paginador.total(lancamentoFilter, manager, predicates));
	}
	
	@Override
	public List<LancamentoEstatisticaPorCategoria> porCategoria(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaPorCategoria> criteriaQuery = criteriaBuilder
				.createQuery(LancamentoEstatisticaPorCategoria.class);
		Root <Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery
			.select(criteriaBuilder.construct(LancamentoEstatisticaPorCategoria.class,
					root.get("categoria"),
					criteriaBuilder.sum(root.get("valor")))
			);
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1); // Primeiro dia do mes
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth()); // Quantidade de dias
		criteriaQuery
			.where(
					criteriaBuilder.greaterThanOrEqualTo(
							root.get("dataVencimento"), primeiroDia),
					criteriaBuilder.lessThanOrEqualTo(
							root.get("dataVencimento"), ultimoDia)
			);
		criteriaQuery.groupBy(root.get("categoria"));
		
		TypedQuery<LancamentoEstatisticaPorCategoria> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	@Override
	public List<LancamentoEstatisticaPorDia> porDia(LocalDate mesReferencia) {
		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		
		CriteriaQuery<LancamentoEstatisticaPorDia> criteriaQuery = criteriaBuilder
				.createQuery(LancamentoEstatisticaPorDia.class);
		Root <Lancamento> root = criteriaQuery.from(Lancamento.class);
		
		criteriaQuery
			.select(criteriaBuilder.construct(LancamentoEstatisticaPorDia.class,
					root.get("tipo"),
					root.get("dataVencimento"),
					criteriaBuilder.sum(root.get("valor")))
			);
		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1); // Primeiro dia do mes
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth()); // Quantidade de dias
		criteriaQuery
			.where(
					criteriaBuilder.greaterThanOrEqualTo(
							root.get("dataVencimento"), primeiroDia),
					criteriaBuilder.lessThanOrEqualTo(
							root.get("dataVencimento"), ultimoDia)
			);
		criteriaQuery.groupBy(root.get("tipo"),root.get("dataVencimento"));
		
		TypedQuery<LancamentoEstatisticaPorDia> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
	}
	
	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			
			predicates.add(builder.like(
					builder.lower(root.get("descricao")),
					"%" + lancamentoFilter.getDescricao().toLowerCase() + "%"
					));
		}
		if(!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoDe())) {
			predicates.add(builder.greaterThanOrEqualTo(
					root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()));
		}
		if(!StringUtils.isEmpty(lancamentoFilter.getDataVencimentoAte())) {
			predicates.add(builder.lessThanOrEqualTo(
					root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte()));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}



}
