package com.alg.brewer.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.controller.validator.VendaValidador;
import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.StatusVenda;
import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.CervejasRepository;
import com.alg.brewer.repositories.filter.VendaFilter;
import com.alg.brewer.security.UsuarioSistema;
import com.alg.brewer.service.CadastroVendaService;
import com.alg.brewer.session.TabelaItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {

	@Autowired
	private CervejasRepository cervejasRepository;
	
	@Autowired
	private TabelaItensSession tabelaItensSession;
	
	@Autowired
	private CadastroVendaService service;
	
	@Autowired
	private VendaValidador vendaValidador;
	
	@InitBinder(value = "venda")
	public void inicializarValidador(WebDataBinder binder) {
		binder.setValidator(vendaValidador);
	}
	
	@GetMapping
	public ModelAndView pesquisar(VendaFilter vendaFilter, BindingResult result, 
			@PageableDefault(size=3) Pageable pageable, HttpServletRequest request) {
		
		ModelAndView mv = new ModelAndView("venda/PesquisaVendas");
		
		
		PageWrapper<Venda> paginaWrapper = new PageWrapper<Venda>(service.filtrar(vendaFilter, pageable), request);
		mv.addObject("pagina", paginaWrapper);
		mv.addObject("status", StatusVenda.values());
		
		return mv;
	}
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		
		if(StringUtils.isEmpty(venda.getUuid())) {			
			venda.setUuid(UUID.randomUUID().toString());
		}
		
		mv.addObject("itens", venda.getItens());
		mv.addObject("valorFrete", venda.getValorFrete());
		mv.addObject("valorDesconto", venda.getValorDesconto());
		mv.addObject("valorTotalItens", tabelaItensSession.getValorTotal(venda.getUuid()));
		return mv;
	}
	
	@PostMapping(value = "/nova", params = "salvar")
	public ModelAndView salvar(Venda venda, BindingResult result, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		this.service.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}

	@PostMapping(value = "/nova", params = "emitir")
	public ModelAndView emitir(Venda venda, BindingResult result, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		this.service.emitir(venda);
		attributes.addFlashAttribute("mensagem", "Venda emitida com sucesso");
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping(value = "/nova", params = "enviarEmail")
	public ModelAndView enviarEmail(Venda venda, BindingResult result, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		validarVenda(venda, result);
		if(result.hasErrors()) {
			return nova(venda);
		}
		
		venda.setUsuario(usuarioSistema.getUsuario());
		
		this.service.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva e email enviado");
		return new ModelAndView("redirect:/vendas/nova");
	}
	
	@PostMapping("/item")
	public ModelAndView adicionarItem(Long codigoCerveja, String uuid) {
		Cerveja cerveja = this.cervejasRepository.findOne(codigoCerveja);
		this.tabelaItensSession.adicionarItem(uuid, cerveja, 1);
		return mvTabelaVendas(uuid);
	}
	
	@PutMapping("/item/{codigoCerveja}")
	public ModelAndView alterarQuantidade(@PathVariable("codigoCerveja") Cerveja cerveja, Integer quantidade, String uuid) {
		tabelaItensSession.alterarQuantidade(uuid, cerveja, quantidade);
		return mvTabelaVendas(uuid);
	}
	
	
	@DeleteMapping("/item/{uuid}/{codigoCerveja}")
	public ModelAndView excluirItem(@PathVariable("codigoCerveja") Cerveja cerveja, @PathVariable("uuid") String uuid) {
		tabelaItensSession.excluirItem(uuid, cerveja);
		return mvTabelaVendas(uuid);
	}
	
	private void validarVenda(Venda venda, BindingResult result) {
		venda.adicionarItens(this.tabelaItensSession.getItens(venda.getUuid()));
		venda.calcularValorTotal();
		
		vendaValidador.validate(venda, result);
	}

	private ModelAndView mvTabelaVendas(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensSession.getItens(uuid));
		mv.addObject("valorTotal", tabelaItensSession.getValorTotal(uuid));
		return mv;
	}
}
