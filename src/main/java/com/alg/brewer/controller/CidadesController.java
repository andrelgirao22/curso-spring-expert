package com.alg.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alg.brewer.controller.page.PageWrapper;
import com.alg.brewer.model.Cerveja;
import com.alg.brewer.model.Cidade;
import com.alg.brewer.repositories.EstadoRepository;
import com.alg.brewer.repositories.filter.CidadeFilter;
import com.alg.brewer.service.CadastroCidadeService;
import com.alg.brewer.service.exception.CidadeException;
import com.alg.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

	@Autowired
	private CadastroCidadeService cidadesService;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public ModelAndView pesquisar(CidadeFilter cidadeFilter, BindingResult result, 
			@PageableDefault(size = 4) Pageable pageable,
			HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("cidade/PesquisaCidades");
		
		PageWrapper<Cidade> pagina = 
				new PageWrapper<Cidade>(this.cidadesService.filtrar(cidadeFilter, pageable), request);
		
		modelAndView.addObject("estados", estadoRepository.findAll());
		modelAndView.addObject("pagina", pagina);
		
		return modelAndView;
	}
	
	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		
		Cidade cidade = cidadesService.buscarComEstado(codigo);
		ModelAndView mv = novo(cidade);
		mv.addObject(cidade);
		return mv;
		
	}
	
	@DeleteMapping("{codigo}")
	public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") Cidade cidade) {
		try {
			cidadesService.excluir(cidade);
		}catch (ImpossivelExcluirEntidadeException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cidade cidade) {
		
		ModelAndView modelAndView = new ModelAndView("cidade/CadastroCidade");
		modelAndView.addObject("estados", this.estadoRepository.findAll());
		return modelAndView;
	}

	@Cacheable(value = "cidades", key = "#codigoEstado")
	@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Cidade> pesquisaPorCodigoEstado(
			@RequestParam(name = "estado", defaultValue = "-1") Long codigoEstado) {
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		
		return this.cidadesService.findByEstadoCodigo(codigoEstado);
	}
	
	@CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
	@PostMapping(value = "/novo")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			//return novo(cidade);
			throw new RuntimeException("erro");
		}
		
		try {
			this.cidadesService.salvar(cidade);
		} catch(CidadeException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return novo(cidade);
		}
		attributes.addFlashAttribute("mensagem", "Cidade cadastrada com sucesso");
		
		return new ModelAndView("redirect:/cidades/novo");
	}
	
}
