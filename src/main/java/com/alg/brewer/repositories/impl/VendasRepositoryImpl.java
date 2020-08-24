package com.alg.brewer.repositories.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.filter.VendaFilter;
import com.alg.brewer.repositories.helper.VendasQueries;
import com.alg.brewer.repositories.paginacao.PaginacaoUtil;

public class VendasRepositoryImpl implements VendasQueries {

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		
		this.paginacaoUtil.preparar(criteria, pageable);
		
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<Venda>(criteria.list(), pageable, total(filtro));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Venda buscarComItens(Long codigo) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return (Venda) criteria.uniqueResult();
	}
	
	private Long total(VendaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Venda.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(VendaFilter filtro, Criteria criteria) {
		
		criteria.createAlias("cliente", "c");
		
		if (filtro != null) {
			
			if (filtro.getCodigo() != null) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}
			
			if(filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}
			
			if (!StringUtils.isEmpty(filtro.getNomeCliente())) {
				criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getCpfOuCnpj())) {
				criteria.add(Restrictions.eq("c.cpfOuCnpj", filtro.getCpfOuCnpj()));
			}

			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorDe()));
			}

			if (filtro.getValorAte() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorAte()));
			}
			
			if(filtro.getDataDe() != null) {
				criteria.add(Restrictions.ge("dataCriacao",  LocalDateTime.of(filtro.getDataDe(), LocalTime.of(0, 0))));
			}
			
			if(filtro.getDataAte() != null) {
				criteria.add(Restrictions.le("dataCriacao", LocalDateTime.of(filtro.getDataAte(), LocalTime.of(23, 59))));
			}
		}
	}

}
