package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/usuarios/new").hasAnyRole("EDITOR")
		.antMatchers("/usuarios/listar").hasAnyRole("ADMIN")
		.antMatchers("/bootstrap-5.1.3-dist/**", "/style/**")
		.permitAll().anyRequest()
		.authenticated().and().formLogin().
		loginPage("/login").permitAll().and().
		logout().permitAll().and()
		.rememberMe();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder)
	  throws Exception {
	   builder
	    .inMemoryAuthentication()
	    .withUser("eduardo").password("$2a$10$IBHDImf7kZOU3vMJGz0kKuhgY0s5250CTGli9Wz53CgHkjnRxGHwO").roles("EDITOR", "ADMIN")
	    .and()
	    .withUser("fernanda").password("$2a$10$IBHDImf7kZOU3vMJGz0kKuhgY0s5250CTGli9Wz53CgHkjnRxGHwO").roles("EDITOR");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	}
}