package com.alg.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

	private Page<T> page;
	
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		//this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
		
		String httpUrl = request.getRequestURL().append(
				request.getQueryString() != null ? "?" + request.getQueryString() : "")
				.toString().replaceAll("\\+", "%20");
		
		this.uriBuilder = ServletUriComponentsBuilder.fromHttpUrl(httpUrl);
	}
	
	public List<T> getConteudo() {
		return this.page.getContent();
	}
	
	public boolean isVazia() {
		return this.getConteudo().isEmpty();
	}
	
	public int getAtual() {
		return this.page.getNumber();
	}
	
	public boolean isPrimeira() {
		return this.page.isFirst();
	}
	
	public boolean isUltima() {
		return this.page.isLast();
	}
	
	public int getTotalPages() {
		return page.getTotalPages();
	}
	
	public String urlParaPagina(int pagina) {
		return this.uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(this.uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade)); 
		
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	public String inverterDirecao(String propriedade) {
		
		String direcao = "asc";
		
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade): null;
		
		if(order != null) {
			direcao = order.getDirection().equals(Sort.Direction.ASC) ? "desc" : "asc";
		}
		
		return direcao;
	}
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade): null;
		
		if(order == null) {
			return false;
		}
	
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}
}
