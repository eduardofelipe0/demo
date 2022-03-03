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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceConfigurer userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/usuarios/**").permitAll()
			.antMatchers("/bootstrap-5.1.3-dist/**", "/style/**")
			.permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("user_nome")
			.passwordParameter("user_senha")
			//.defaultSuccessUrl("entrada/home")
			.permitAll()
		.and()
			.logout()
			.permitAll()
		.and()
			.rememberMe();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	      return new BCryptPasswordEncoder();
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
	
	/*@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder)
	  throws Exception {
	   builder
	    .inMemoryAuthentication()
	    .withUser("eduardo").password("$2a$10$IBHDImf7kZOU3vMJGz0kKuhgY0s5250CTGli9Wz53CgHkjnRxGHwO").roles("EDITOR", "ADMIN")
	    .and()
	    .withUser("fernanda").password("$2a$10$IBHDImf7kZOU3vMJGz0kKuhgY0s5250CTGli9Wz53CgHkjnRxGHwO").roles("EDITOR");
	} */
	
}