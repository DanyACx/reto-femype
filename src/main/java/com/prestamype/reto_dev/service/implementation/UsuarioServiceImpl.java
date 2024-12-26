package com.prestamype.reto_dev.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamype.reto_dev.dao.UsuarioRepository;
import com.prestamype.reto_dev.persistence.entity.Usuario;
import com.prestamype.reto_dev.presentation.dto.UsuarioDTO;
import com.prestamype.reto_dev.service.interfaces.IUsuarioService;

@Service
public class UsuarioServiceImpl implements IUsuarioService{

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> getUsuarios(){
		return usuarioRepository.findAll();
	}
	
	public Usuario create(UsuarioDTO usuarioDTO) {
		
		Usuario usuario = Usuario.builder()
				.nombre(usuarioDTO.getNombre())
				.edad(usuarioDTO.getEdad())
				.cargo(usuarioDTO.getCargo())
				.build();
		
		
		return usuarioRepository.save(usuario);
	}
	
	public Optional<Usuario> updateUsuario(String id, Usuario usuario){
		if(!usuarioRepository.existsById(id)) {
			return Optional.empty();
		}
		
		usuario.setId(id);
		return Optional.of(usuarioRepository.save(usuario));
	}
	
	public void deleteUsuario(String id) {
		usuarioRepository.deleteById(id);
	}
}
