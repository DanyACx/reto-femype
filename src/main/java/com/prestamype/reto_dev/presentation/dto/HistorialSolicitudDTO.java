package com.prestamype.reto_dev.presentation.dto;

import com.prestamype.reto_dev.persistence.entity.TasaCambio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialSolicitudDTO {

	private String id;
	private String tipodecambio;
	private double montoenviar;
	private double montorecibir;
	private String idusuario;
	private TasaCambio tasacambio;
}
