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

import com.example.demo.model.Role;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceConfigurer userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
			.antMatchers("/usuarios/**").hasAuthority(Role.ADMIN.name())
			.antMatchers("/bootstrap-5.1.3-dist/**", "/style/**")
			.permitAll()
			.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("user_nome")
			.passwordParameter("user_senha")
			.defaultSuccessUrl("/home") //hasAuthority(Role.ADMIN.getDescricao()) hasAnyRole("ADMIN")
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
}