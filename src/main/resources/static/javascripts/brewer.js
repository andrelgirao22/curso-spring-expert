$(function() {
	
	var decimal = $('.js-decimal');
	//123456
	decimal.maskMoney({decimal: ',', thousands: '.'});

	var plain = $('.js-plain');
	plain.maskMoney({precision : 0, thousands: '.'});
	
});