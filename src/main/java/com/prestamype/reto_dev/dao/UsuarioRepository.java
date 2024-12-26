package com.prestamype.reto_dev.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prestamype.reto_dev.persistence.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

}
