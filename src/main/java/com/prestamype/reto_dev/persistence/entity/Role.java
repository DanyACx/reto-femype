package com.prestamype.reto_dev.persistence.entity;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role {

	@Id
	@Field("_id")
	private String id;
	@Field("role_nombre")
	private String rolenombre;
	@Field("role_descripcion")
	private String roledescripcion;
	@Field("role_codigo")
	private String rolecodigo;
	//private Set<Permiso> permisos;
}
