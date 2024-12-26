package com.prestamype.reto_dev.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private String id;
	private String nombre;
	private Integer edad;
	private String cargo;
}
