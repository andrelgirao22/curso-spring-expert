package com.alg.brewer.repositories.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alg.brewer.model.Grupo;
import com.alg.brewer.model.Usuario;
import com.alg.brewer.model.UsuarioGrupo;
import com.alg.brewer.repositories.filter.UsuarioFilter;
import com.alg.brewer.repositories.helper.UsuariosQueries;
import com.alg.brewer.repositories.paginacao.PaginacaoUtil;

public class UsuariosRepositoryImpl implements UsuariosQueries {

	@Autowired
	private EntityManager manager;
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	@Override
	public Optional<Usuario> porEmailEAtivo(String email) {
		return manager
				.createQuery("from Usuario where lower(email) = lower(:email) and ativo = true", Usuario.class)
				.setParameter("email", email)
				.getResultList().stream().findFirst();
	}

	@Override
	public List<String> permissoes(Usuario usuario) {
		return this.manager
				.createQuery("select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u =:usuario", String.class)
				             //"select distinct p.nome from Usuario u inner join u.grupos g inner join g.permissoes p where u = :usuario", String.class)
				.setParameter("usuario", usuario)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Usuario> filtrar(UsuarioFilter usuarioFilter, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(usuarioFilter, criteria);
		
		List<Usuario> filtrados= criteria.list();
		filtrados.forEach(u -> Hibernate.initialize(u.getGrupos()));
		
		
		return new PageImpl<Usuario>(filtrados, pageable, total(usuarioFilter));
	}
	
	private void adicionarFiltro(UsuarioFilter filtro, Criteria criteria) {
		
		if (filtro != null) {
			if(!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if(!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.START));
			}
			
			if(filtro.getGrupos() != null && !filtro.getGrupos().isEmpty()) {
				List<Criterion> subqueries = new ArrayList<>();
				for(Long codigoGrupo : filtro.getGrupos().stream().mapToLong(Grupo::getCodigo).toArray()) {
					DetachedCriteria dc = DetachedCriteria.forClass(UsuarioGrupo.class);
					dc.add(Restrictions.eq("id.grupo.codigo", codigoGrupo));
					
					dc.setProjection(Projections.property("id.usuario"));
					subqueries.add(Subqueries.propertyIn("codigo", dc));
				}
				
				Criterion[] criterions = new Criterion[subqueries.size()];
				criteria.add(Restrictions.and(subqueries.toArray(criterions)));
			}
		}
	}
	
	private Long total(UsuarioFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Usuario.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}
}
