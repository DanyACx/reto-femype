package com.prestamype.reto_dev.util;

import lombok.Data;

@Data
public class ExternalData {

	private double sunat_purchase_price;
    private double sunat_sale_price;
    private boolean status;
    private String _id;
    private double purchase_price;
    private double sale_price;
    private double purchase_price_comparative;
    private double sale_price_comparative;
    private double purchase_price_paralelo;
    private double sale_price_paralelo;
}
