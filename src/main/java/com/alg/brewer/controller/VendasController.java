package com.alg.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.Venda;
import com.alg.brewer.repositories.CervejasRepository;
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
	
	@GetMapping("/nova")
	public ModelAndView nova(Venda venda) {
		ModelAndView mv = new ModelAndView("venda/CadastroVenda");
		venda.setUuid(UUID.randomUUID().toString());
		return mv;
	}
	
	@PostMapping("/nova")
	public ModelAndView salvar(Venda venda, RedirectAttributes attributes, 
			@AuthenticationPrincipal UsuarioSistema usuarioSistema) {

		venda.setUsuario(usuarioSistema.getUsuario());
		venda.adicionarItens(this.tabelaItensSession.getItens(venda.getUuid()));
		
		this.service.salvar(venda);
		attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
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

	private ModelAndView mvTabelaVendas(String uuid) {
		ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
		mv.addObject("itens", tabelaItensSession.getItens(uuid));
		mv.addObject("valorTotal", tabelaItensSession.getValorTotal(uuid));
		return mv;
	}
}
