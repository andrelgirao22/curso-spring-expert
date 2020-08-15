package com.alg.brewer.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.alg.brewer.mail.Mailer;

@Configuration
@ComponentScan(basePackageClasses =  Mailer.class)
@PropertySource({"classpath:env/mail-${envTarget:local}.properties"})
//@PropertySource(value = { "file:\\${USERPROFILE}\\.brewer-mail.properties" }, ignoreResourceNotFound = true)
public class MailConfig {

	@Autowired
	private Environment env;
	
	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);
		mailSenderImpl.setUsername(env.getProperty("email.username"));
		mailSenderImpl.setPassword(env.getProperty("email.password"));
		
		System.out.println("user " + env.getProperty("email.password"));
		
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", true);
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.debug", false);
		props.put("mail.smtp.connectiontimeout", 10000); // miliseconds

		mailSenderImpl.setJavaMailProperties(props);
		
		return mailSenderImpl;
	}
	
	
}
