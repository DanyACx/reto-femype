package com.prestamype.reto_dev.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.stereotype.Service;

import com.prestamype.reto_dev.persistence.entity.TasaCambio;
import com.prestamype.reto_dev.presentation.dto.TasaCambioDTO;
import com.prestamype.reto_dev.service.interfaces.ITasaCambioService;

@Service
public class TasaCambioServiceImpl implements ITasaCambioService{

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public TasaCambio crearTasaCambio(TasaCambioDTO tasaCambioDTO) {
		
		updateAllStatusesToFalse(); // para cambiar todos documentos a estado FALSE (inactivos)

		TasaCambio tasaCambio = TasaCambio.builder()
				.status(tasaCambioDTO.getStatus())
				.sunatpurchaseprice(tasaCambioDTO.getSunatpurchaseprice())
				.sunatsaleprice(tasaCambioDTO.getSunatsaleprice())
				.purchaseprice(tasaCambioDTO.getPurchaseprice())
				.saleprice(tasaCambioDTO.getSaleprice())
				.purchasepricecomparative(tasaCambioDTO.getPurchasepricecomparative())
				.salepricecomparative(tasaCambioDTO.getSalepricecomparative())
				.purchasepriceparalelo(tasaCambioDTO.getPurchasepriceparalelo())
				.salepriceparalelo(tasaCambioDTO.getSalepriceparalelo())
				.build();

		mongoTemplate.insert(tasaCambio);

		return tasaCambio;
	}
	
	public List<TasaCambio> getTasasCambio(){
		return mongoTemplate.findAll(TasaCambio.class);
	}
	
	public void updateAllStatusesToFalse() {
	    Query query = new Query(); // Criterio vacío para afectar a todos los documentos
	    Update update = new Update().set("status", false);

	    // Ejecutar la actualización masiva
	    mongoTemplate.updateMulti(query, update, "tasa_cambio");
	}
	
}
