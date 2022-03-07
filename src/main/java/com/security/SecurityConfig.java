package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.security.filters.CustomAuthenticationFIlter;
import com.security.filters.CustomAuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers("/login").permitAll();
		http.authorizeRequests().antMatchers("/registration/**").permitAll();
		http.authorizeRequests().antMatchers("/user/**").access("hasRole('ADMIN')");
		http.authorizeRequests().antMatchers("/invitation/**").access("hasRole('COMPANY')");
		http.authorizeRequests().antMatchers("/forgetPassword/**").permitAll();
		/********************************************Meriem **********************************************/
		/*http.authorizeRequests()
				.antMatchers("/voyage/addVoyage", "/voyage/updateVoyage/**", "/voyage/deleteVoyage/**",
						"/voyage/priceFromDate/**", "/voyage/priceRecap/**", "/voyage/addEmployee/**", "/voyage/afficherPDF/**")
				.access("hasRole('COMPANY')");
		http.authorizeRequests().antMatchers("/voyage/getVoyages").access("hasRole('ADMIN')");
		http.authorizeRequests().antMatchers("/voyage/findVDestionation/**", "/voyage/date/**").permitAll();
		http.authorizeRequests()
				.antMatchers("/voyage/matchProfession/**", "/voyage/MatchUsers/**", "/voyage/SuggestionUser/**")
				.access("hasRole('EMPLOYEE')");*/
		
		http.authorizeRequests().antMatchers("/voyage/**").permitAll();

		http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new CustomAuthenticationFIlter(authenticationManagerBean()));
		http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
