package com.prestamype.reto_dev.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.prestamype.reto_dev.configuration.jwt.JwtUtils;
import com.prestamype.reto_dev.dao.AuthLoginRequest;
import com.prestamype.reto_dev.dao.UserAuthRepository;
import com.prestamype.reto_dev.persistence.entity.UserAuth;
import com.prestamype.reto_dev.presentation.dto.AuthResponse;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	/*@Autowired
	private UserAuthRepository userAuthRepository;*/
	
	@Autowired
	private UserAuthServiceImpl userAuthServiceImpl;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserAuth userAuth = userAuthServiceImpl.findByEmailWithRoles(email)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + email + " no existe."));
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		userAuth.getRoles()
			.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRolenombre())))); // considerar el "ROLE_"
		
		
		return new User(userAuth.getEmail(),
				userAuth.getPassword(),
				userAuth.isEnabled(),
				userAuth.isAccountNoExpired(),
				userAuth.isCredentialNoExpired(),
				userAuth.isAccountNoLocked(),
				authorityList);
	}
	
	public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
		String email = authLoginRequest.email();
		String password = authLoginRequest.password();
		
		Authentication authentication = this.authenticate(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(email, "user logueado con exito :)", accessToken, true);
		
		return authResponse;
	}
	
	public Authentication authenticate(String email, String password) {
		UserDetails userDetails = this.loadUserByUsername(email);
		
		if(userDetails == null) {
			throw new BadCredentialsException("email o password invalido :(");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("password invalido");
		}
		
		return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
	}
}
