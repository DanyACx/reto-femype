package com.prestamype.reto_dev.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TasaCambioDTO {

	private String id;
	private Boolean status;
	private double sunatpurchaseprice;
	private double sunatsaleprice;
	private double purchaseprice;
	private double saleprice;
	private double purchasepricecomparative;
	private double salepricecomparative;
	private double purchasepriceparalelo;
	private double salepriceparalelo;
}
