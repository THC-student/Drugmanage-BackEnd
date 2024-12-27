package com.example.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Config {
	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}
}