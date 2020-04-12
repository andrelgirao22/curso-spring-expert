$(function() {
	
	var decimal = $('.js-decimal');
	//123456
	decimal.maskMoney();

	var plain = $('.js-plain');
	plain.maskMoney({precision : 0});
	
});