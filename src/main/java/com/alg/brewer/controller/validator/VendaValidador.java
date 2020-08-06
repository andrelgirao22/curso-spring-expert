package com.alg.brewer.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alg.brewer.model.Venda;

@Component
public class VendaValidador implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida");
		
		Venda venda = (Venda) target;
		
		this.validarSeInformouApenasHorarioDeEntrega(errors, venda);
		this.validarSeInformouItens(errors, venda);
		this.validarValorTotalNegativo(errors, venda);
		
	}

	private void validarValorTotalNegativo(Errors errors, Venda venda) {
		if(venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "Valor total não pode ser negativo");
		}
	}

	private void validarSeInformouItens(Errors errors, Venda venda) {
		if(venda.getItens().isEmpty()) {
			errors.reject("","Adicione pelo menos uma cerveja na venda");
		}
	}

	private void validarSeInformouApenasHorarioDeEntrega(Errors errors, Venda venda) {
		if(venda.getHoraEntrega() != null && venda.getDataEntrega() == null) {
			errors.rejectValue("dataEntrega", "", "Informe uma data de entreega para um horário");
		}
	}

}
