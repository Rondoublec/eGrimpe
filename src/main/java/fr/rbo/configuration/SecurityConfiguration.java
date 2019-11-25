package fr.rbo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth
			.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.
			authorizeRequests()
// gestion des spots
				.antMatchers("/spot/add").authenticated()
				.antMatchers("/spot/delete/**").authenticated()
				.antMatchers("/spot/edit/**").authenticated()
				.antMatchers("/spot/addComment").authenticated()
				.antMatchers("/spot/updateComment").authenticated()
				.antMatchers("/spot/deleteComment").authenticated()
				.antMatchers("/spot/addSecteur").authenticated()
				.antMatchers("/spot/deleteSecteur").authenticated()
				.antMatchers("/secteur/addVoie").authenticated()
				.antMatchers("/secteur/deleteVoie").authenticated()
				.antMatchers("/voie/addLongueur").authenticated()
				.antMatchers("/voie/deleteLongueur").authenticated()
				.antMatchers("/spot/save").authenticated()
// gestion des topos
				.antMatchers("/topo/add").authenticated()
				.antMatchers("/topo/delete/**").authenticated()
				.antMatchers("/topo/edit/**").authenticated()
				.antMatchers("/topo/save").authenticated()
				.antMatchers("/topo/demande").authenticated()
				.antMatchers("/topo/accepte").authenticated()
				.antMatchers("/topo/annule").authenticated()
				.antMatchers("/mestopos").authenticated()

				.antMatchers("/adm1n/**").authenticated()

				.antMatchers("/adm1n**","/adm1n/**").hasAuthority("ADMIN")

				.antMatchers("/**").permitAll()
				.antMatchers("/").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/registration").permitAll().anyRequest()

				.authenticated().and().csrf().disable().formLogin()
				.loginPage("/login").failureUrl("/login?error=true")
				.defaultSuccessUrl("/home")
				.usernameParameter("email")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/").and().exceptionHandling()
				.accessDeniedPage("/access-denied");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

}