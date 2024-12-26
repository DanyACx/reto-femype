package com.prestamype.reto_dev.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prestamype.reto_dev.dao.AuthLoginRequest;
import com.prestamype.reto_dev.presentation.dto.AuthResponse;
import com.prestamype.reto_dev.service.implementation.UserDetailServiceImpl;


@RestController
@RequestMapping("/api/v1/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;
	
	@PostMapping("/log-in")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest userRequest) {
		return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.OK);
	}
}
