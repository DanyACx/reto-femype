package com.prestamype.reto_dev.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.prestamype.reto_dev.persistence.entity.UserAuth;

public interface UserAuthRepository extends MongoRepository<UserAuth, String>{

	Optional<UserAuth> findByEmail(String email);
}
