var Brewer = Brewer || {};

Brewer.MascaraCpfCnpj = (function() {
	
	function MascaraCpfCnpj() {
		this.radioTipoPessoa = $('.js-radio-tipo-pessoa');
	}
	
	MascaraCpfCnpj.prototype.iniciar = function() {
		this.radioTipoPessoa.on('change', onTipoPessoaAlterado.bind(this));
		this.labelCpfCnpj = $('[for=cpfOuCnpj]');
		this.inputCpfCnpj = $('#cpfOuCnpj');
	}
	
	function onTipoPessoaAlterado(evento) {
		var tipoPessoaSelecionada = $(evento.currentTarget);
		this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));
		this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
		this.inputCpfCnpj.val('');
		this.inputCpfCnpj.removeAttr('disabled');
	}
	
	return MascaraCpfCnpj;
	
}());

$(function() {
	
	var mascaraCpfCnpj = new Brewer.MascaraCpfCnpj();
	mascaraCpfCnpj.iniciar();
	
});