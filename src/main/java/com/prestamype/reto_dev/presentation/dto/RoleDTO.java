package com.prestamype.reto_dev.presentation.dto;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

	@Id
	private String id;
	private String rolenombre;
	private String roledescripcion;
	private String rolecodigo;
}
