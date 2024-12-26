package com.prestamype.reto_dev.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.prestamype.reto_dev.persistence.entity.Usuario;
import com.prestamype.reto_dev.presentation.dto.UsuarioDTO;

public interface IUsuarioService {

	public List<Usuario> getUsuarios();
	
	public Usuario create(UsuarioDTO usuarioDTO);
	
	public Optional<Usuario> updateUsuario(String id, Usuario usuario);
	
	public void deleteUsuario(String id);
}
