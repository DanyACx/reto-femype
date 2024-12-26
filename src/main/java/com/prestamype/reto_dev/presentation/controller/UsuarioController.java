package com.prestamype.reto_dev.presentation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prestamype.reto_dev.persistence.entity.UserAuth;
import com.prestamype.reto_dev.persistence.entity.Usuario;
import com.prestamype.reto_dev.presentation.dto.UserAuthDTO;
import com.prestamype.reto_dev.presentation.dto.UsuarioDTO;
import com.prestamype.reto_dev.service.interfaces.IUserAuthService;
import com.prestamype.reto_dev.service.interfaces.IUsuarioService;


@RestController
@RequestMapping("/api/v1/usuario")
@PreAuthorize("denyAll()")
public class UsuarioController {

	//private IUsuarioService usuarioService;
	private IUserAuthService userAuthService;
	
	public UsuarioController() {}
	
	@Autowired
	public UsuarioController(/*IUsuarioService usuarioService,*/ IUserAuthService userAuthService) {
		//this.usuarioService = usuarioService;
		this.userAuthService = userAuthService;
	}
	
	/*@GetMapping("/listarUsuarios")
	public List<Usuario> getUsuarios(){
		return usuarioService.getUsuarios();
	}
	
	@PostMapping("/crearUsuario")
	public ResponseEntity<Usuario> createUsuario(@RequestBody UsuarioDTO usuarioDTO){
		Usuario createdUsuario = usuarioService.create(usuarioDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUsuario);
	}
	
	@PutMapping("/usuario/{id}")
	public ResponseEntity<Usuario> updateUsuario(@PathVariable String id, @RequestBody Usuario usuario){
		return usuarioService.updateUsuario(id, usuario)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.ok().build());
	}
	
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity<?> deleteUsuario(@PathVariable String id){
		
		try {
			usuarioService.deleteUsuario(id);
			return new ResponseEntity<>("Usuario a sido eliminado", HttpStatus.CREATED);
			
		} catch (Exception e) {
			// Maneja otras excepciones no previstas y devuelve un error 500 (INTERNAL_SERVER_ERROR)
	        System.err.println(e.getMessage());
	        return new ResponseEntity<>("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}*/
	
	@PostMapping("/crearUser")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> insertarUserAuth(@RequestBody UserAuthDTO userAuthDTO) {
		
		try {
			userAuthService.insertUser(userAuthDTO);
			return new ResponseEntity<>("Se registro con exito", HttpStatus.CREATED);
		} catch (Exception e) {
			// Maneja la excepción y responde con un código específico
			System.err.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/listarUsuarios")
	@PreAuthorize("hasRole('ADMIN')")
	//@PreAuthorize("hasRole('USER')")
	public List<UserAuth> listarUsuarios(){
		List<UserAuth> usuarios = userAuthService.getUsuarios();
	        return usuarios;
	}
}
