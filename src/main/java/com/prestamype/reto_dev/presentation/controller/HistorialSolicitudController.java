package com.prestamype.reto_dev.presentation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prestamype.reto_dev.persistence.entity.HistorialSolicitud;
import com.prestamype.reto_dev.presentation.dto.HistorialSolicitudDTO;
import com.prestamype.reto_dev.service.interfaces.IHistorialSolicitudService;
import com.prestamype.reto_dev.util.ApiResponse;
import com.prestamype.reto_dev.util.ExternalResponse;

@RestController
@RequestMapping("/api/v1/historialSolicitud")
@PreAuthorize("denyAll()")
public class HistorialSolicitudController {

	private IHistorialSolicitudService historialSolicitudService;

	public HistorialSolicitudController() {
	}

	@Autowired
	public HistorialSolicitudController(IHistorialSolicitudService historialSolicitudService) {
		this.historialSolicitudService = historialSolicitudService;
	}
	
	@GetMapping("/apiExterna")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<ExternalResponse>> getAPIexterna(){
		ExternalResponse usuarios = historialSolicitudService.getAPIExterna();
		
	    ApiResponse<ExternalResponse> response = new ApiResponse<>(
	        "Se registró con éxito",
	        usuarios,
	        HttpStatus.CREATED.value()
	    );

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/crearHistoria")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse<HistorialSolicitud>> insertarHistorialSolicitud(@RequestBody HistorialSolicitudDTO historialSolicitudDTO) {
		
		HistorialSolicitud aux = historialSolicitudService.insertarHistorial(historialSolicitudDTO);
		
		ApiResponse<HistorialSolicitud> response = new ApiResponse<>(
		        "Se registró con éxito",
		        aux,
		        HttpStatus.CREATED.value()
		    );

		    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping("/crear")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENTE')")
	public ResponseEntity<?> insertarSolicitud(@RequestBody HistorialSolicitudDTO historialSolicitudDTO) {
		
		try {
			historialSolicitudService.insertar(historialSolicitudDTO);
			return new ResponseEntity<>("Se registro con exito", HttpStatus.CREATED);
		} catch (Exception e) {
			// Maneja la excepción y responde con un código específico
			System.err.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/misSolicitudes/{idusuario}")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENTE')")
	public List<HistorialSolicitud> listarUsuarios(@PathVariable("idusuario") String idUser){
		List<HistorialSolicitud> solicitudes = historialSolicitudService.solitudesPorUsuario(idUser);
	    return solicitudes;
	}
	
	@DeleteMapping("/eliminarSolicitud/{id}")
	@PreAuthorize("hasRole('ADMIN') OR hasRole('CLIENTE')")
	public ResponseEntity<?> eliminarSolicitud(@PathVariable("id") String id){
		try {
			if(historialSolicitudService.eliminarPorId(id)) {
				return new ResponseEntity<>("Se elimino con exito", HttpStatus.OK);
			}else{
				return new ResponseEntity<>("No se encontro registro", HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
