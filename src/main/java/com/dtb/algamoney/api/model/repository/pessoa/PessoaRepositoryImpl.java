package com.dtb.algamoney.api.model.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import com.dtb.algamoney.api.model.entity.Pessoa;
import com.dtb.algamoney.api.model.entity.Pessoa_;
import com.dtb.algamoney.api.model.repository.filter.PessoaFilter;
import com.dtb.algamoney.api.model.repository.utils.Paginador;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery{
	@Autowired
	private EntityManager manager;
	@Override
	public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class); 
		Root<Pessoa> root = criteria.from(Pessoa.class);
		Predicate[] predicates = criarRestricoes(pessoaFilter, builder, root);
		criteria.where(predicates);
		TypedQuery<Pessoa> query = manager.createQuery(criteria);
		Paginador<PessoaFilter> paginador = new Paginador<>();
		paginador.adicionarRestricoesDePaginacao(query,pageable);
		return new PageImpl<Pessoa>(query.getResultList(),pageable,
				paginador.total(pessoaFilter, manager, predicates));
	}
	private Predicate[] criarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
		List<Predicate> predicates = new ArrayList<>();
		if(!StringUtils.isEmpty(pessoaFilter.getNome())) {
			predicates.add(builder.like(
					builder.lower(root.get(Pessoa_.nome)), "%"+pessoaFilter.getNome().toLowerCase()+"%"));
		}
		if(!StringUtils.isEmpty(pessoaFilter.getAtivo())) {
			predicates.add(builder.equal(
					root.get(Pessoa_.ativo),
					pessoaFilter.getAtivo()
					));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
