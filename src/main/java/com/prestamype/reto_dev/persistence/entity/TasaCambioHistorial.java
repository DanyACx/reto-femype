package com.prestamype.reto_dev.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TasaCambioHistorial {

	private String id;
	private double purchaseprice;
	private double saleprice;
}
