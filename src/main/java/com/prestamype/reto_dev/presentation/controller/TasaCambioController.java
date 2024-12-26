package com.prestamype.reto_dev.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prestamype.reto_dev.persistence.entity.TasaCambio;
import com.prestamype.reto_dev.presentation.dto.TasaCambioDTO;
import com.prestamype.reto_dev.service.interfaces.ITasaCambioService;

@RestController
@RequestMapping("/api/v1/tasaCambio")
@PreAuthorize("denyAll()")
public class TasaCambioController {

	private ITasaCambioService tasaCambioService;

	public TasaCambioController() {}

	@Autowired
	public TasaCambioController( ITasaCambioService tasaCambioService) {
		this.tasaCambioService = tasaCambioService;
	}

	@PostMapping("/crear")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insertarUserAuth(@RequestBody TasaCambioDTO tasaCambioDTO) {

		try {
			tasaCambioService.crearTasaCambio(tasaCambioDTO);
			return new ResponseEntity<>("Se registro con exito", HttpStatus.CREATED);
		} catch (Exception e) {
			// Maneja la excepción y responde con un código específico
			System.err.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listar")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENTE')")
	public List<TasaCambio> listarTasasCambio() {
		List<TasaCambio> tasasCambio = tasaCambioService.getTasasCambio();
		return tasasCambio;
	}
}
