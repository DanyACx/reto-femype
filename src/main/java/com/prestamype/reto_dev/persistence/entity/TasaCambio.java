package com.prestamype.reto_dev.persistence.entity;

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
@Document(collection = "tasa_cambio")
public class TasaCambio {

	@Id
	@Field("_id")
	private String id;
	private Boolean status;
	@Field("sunat_purchase_price")
	private double sunatpurchaseprice;
	@Field("sunat_sale_price")
	private double sunatsaleprice;
	@Field("purchase_price")
	private double purchaseprice;
	@Field("sale_price")
	private double saleprice;
	@Field("purchase_price_comparative")
	private double purchasepricecomparative;
	@Field("sale_price_comparative")
	private double salepricecomparative;
	@Field("purchase_price_paralelo")
	private double purchasepriceparalelo;
	@Field("sale_price_paralelo")
	private double salepriceparalelo;
	
}
