package com.prestamype.reto_dev.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TasaCambioHistorialDTO {

	private String id;
	private double purchaseprice;
	private double saleprice;

}

