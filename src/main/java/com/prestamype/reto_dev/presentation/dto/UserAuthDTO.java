package com.prestamype.reto_dev.presentation.dto;

import java.util.Set;

import com.prestamype.reto_dev.persistence.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDTO {

	private String id;
	private String email;
	private String password;
	private String fecharegistro;
	private boolean isenabled;
	private boolean accountNoExpired;
	private boolean accountNoLocked;
	private boolean credentialNoExpired;
	private Set<Role> roles;
}
