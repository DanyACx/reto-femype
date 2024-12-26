package com.prestamype.reto_dev.persistence.entity;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "user_auth")
public class UserAuth {

	@Id
	@Field("_id")
	private String id;
	private String email;
	private String password;
	@Field("fecha_registro")
	private String fecharegistro;
	@Field("is_enabled")
	private boolean isEnabled;
	@Field("account_no_expired")
	private boolean accountNoExpired;
	@Field("account_no_locked")
	private boolean accountNoLocked;
	@Field("credencial_no_expired")
	private boolean credentialNoExpired;
	private Set<Role> roles;
}
