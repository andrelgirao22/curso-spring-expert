package com.alg.brewer.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alg.brewer.model.Venda;

@Component
public class Mailer {

	@Autowired
	private JavaMailSender sender;
	
	@Async
	public void enviar(Venda venda) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("andrelgirao29@gmail.com");
		message.setTo(venda.getCliente().getEmail());
		message.setSubject("Venda efetuada");
		message.setText("Obrigado " + venda.getCliente().getNome() + " sua venda foi processada!");
		
		this.sender.send(message);
		
	}
	
}
