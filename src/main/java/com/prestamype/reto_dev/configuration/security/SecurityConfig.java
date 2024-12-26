package com.prestamype.reto_dev.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.prestamype.reto_dev.configuration.jwt.JwtTokenValidator;
import com.prestamype.reto_dev.configuration.jwt.JwtUtils;
import com.prestamype.reto_dev.service.implementation.UserDetailServiceImpl;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	//@Autowired
		//private JwtUtils jwtUtils; //  Este es un uso común, aunque menos recomendado por cuestiones de claridad y testabilidad.
		
		private final JwtUtils jwtUtils;
		
		//@Autowired
		public SecurityConfig(JwtUtils jwtUtils) { // Es la forma más recomendada, ya que promueve la inmutabilidad y facilita la realización de pruebas
	        this.jwtUtils = jwtUtils;
	    }

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
			return httpSecurity
					.csrf(csrf -> csrf.disable())
					//.httpBasic(Customizer.withDefaults())
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class) // antes del filtro de autenticacion
					.build();
		}

		// configuramos securityFilterChain
		/*
		 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity
		 * httpSecurity) throws Exception { // httpSecurity pasa por todos los filtros
		 * return httpSecurity .csrf(csrf -> csrf.disable()) // con rest no se necesita,
		 * con mvc si .httpBasic(Customizer.withDefaults()) .sessionManagement(session
		 * -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 * .authorizeHttpRequests(http -> {
		 * 
		 * //configurar los endpoint publicos http.requestMatchers(HttpMethod.GET,
		 * "/auth/hello").permitAll();
		 * 
		 * //configurar los endposint privados http.requestMatchers(HttpMethod.GET,
		 * "/auth/hello-security").hasAuthority("READ"); // tener la autorizacion
		 * 
		 * // configurar el resto de endpoitns - NO ESPECIFICADOS
		 * http.anyRequest().denyAll(); // rechaza todo lo demas
		 * //http.anyRequest().authenticated(); // basta con que te encuentres
		 * autenticado }) .build();
		 * 
		 * // SessionCreationPolicy.STATELESS: trabajar sin estado (no se guarda sesion
		 * en memoria), el tiempo de session lo va dar el token }
		 */

		@Bean
		public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
				throws Exception {
			return authenticationConfiguration.getAuthenticationManager();
		}

		@Bean
		public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailServiceImpl) { // se conecta a la base de datos y trae el usuario
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setPasswordEncoder(passwordEncoder());
			provider.setUserDetailsService(userDetailServiceImpl);
			return provider;
		}

		/*@Bean
		public UserDetailsService userDetailsService() {
			UserDetails userDetails = User.withUsername("dany").password("12345").roles("ADMIN")
					.authorities("READ", "CREATE").build();

			return new InMemoryUserDetailsManager(userDetails);
		}*/

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
}
