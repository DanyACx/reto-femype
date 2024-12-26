package com.prestamype.reto_dev.service.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.prestamype.reto_dev.persistence.entity.HistorialSolicitud;
import com.prestamype.reto_dev.persistence.entity.TasaCambio;
import com.prestamype.reto_dev.persistence.entity.TasaCambioHistorial;
import com.prestamype.reto_dev.presentation.dto.HistorialSolicitudDTO;
import com.prestamype.reto_dev.service.interfaces.IHistorialSolicitudService;
import com.prestamype.reto_dev.util.ExternalResponse;

@Service
public class HistorialSolicitudServiceImpl implements IHistorialSolicitudService {

	@Autowired
	private ExternalService externalService;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public ExternalResponse getAPIExterna() {
		ExternalResponse response = externalService.fetchData();

		return response;
	}

	public HistorialSolicitud insertarHistorial(HistorialSolicitudDTO historialSolicitudDTO) {

		double montorecibir;
		// ExternalResponse res = getAPIExterna();

		TasaCambioHistorial tasaCambioHistorial = TasaCambioHistorial.builder().id(getAPIExterna().getData().get_id())
				.purchaseprice(getAPIExterna().getData().getPurchase_price())
				.saleprice(getAPIExterna().getData().getSale_price()).build();

		if (historialSolicitudDTO.getTipodecambio().equals("compra")) {
			montorecibir = historialSolicitudDTO.getMontoenviar() * getAPIExterna().getData().getPurchase_price();
		} else {
			montorecibir = historialSolicitudDTO.getMontoenviar() / getAPIExterna().getData().getSale_price();
		}

		HistorialSolicitud historialSolicitud = HistorialSolicitud.builder()
				.tipodecambio(historialSolicitudDTO.getTipodecambio())
				.montoenviar(historialSolicitudDTO.getMontoenviar()).montorecibir(montorecibir)
				.idusuario(historialSolicitudDTO.getIdusuario()).tasacambiohistorial(tasaCambioHistorial).build();

		mongoTemplate.insert(historialSolicitud);

		return historialSolicitud;
	}

	public void insertar(HistorialSolicitudDTO historialSolicitudDTO) {

		double montorecibir;
		Optional<HashMap<String, Object>> result = busquedaTasaActiva();

		if (result.isPresent()) { // validamos que exista unica tasa activa (status == true)
			HashMap<String, Object> documentData = result.get();

			TasaCambioHistorial tasaCambioHistorial = TasaCambioHistorial.builder()
					.id((String) documentData.get("id"))
					.purchaseprice((Double) documentData.get("purchaseprice"))
					.saleprice((Double) documentData.get("saleprice"))
					.build();

			if (historialSolicitudDTO.getTipodecambio().equals("compra")) {
				montorecibir = historialSolicitudDTO.getMontoenviar() * tasaCambioHistorial.getPurchaseprice();
			} else {
				montorecibir = historialSolicitudDTO.getMontoenviar() / tasaCambioHistorial.getSaleprice();
			}

			HistorialSolicitud historialSolicitud = HistorialSolicitud.builder()
					.tipodecambio(historialSolicitudDTO.getTipodecambio())
					.montoenviar(historialSolicitudDTO.getMontoenviar()).montorecibir(montorecibir)
					.idusuario(historialSolicitudDTO.getIdusuario()).tasacambiohistorial(tasaCambioHistorial)
					.build();

			mongoTemplate.insert(historialSolicitud);

			//return historialSolicitud;
		} else {
			System.out.println("Hay mas de una Tasa de Cambio activa o no se encontro ninguna");
		}

	}

	public Optional<HashMap<String, Object>> busquedaTasaActiva() {
		Query query = new Query();
		query.addCriteria(Criteria.where("status").is(true));

		// Encuentra todos los documentos que coincidan con el criterio
		var documents = mongoTemplate.find(query, TasaCambio.class, "tasa_cambio");

		if (documents.size() == 1) {
			TasaCambio document = documents.get(0);
			HashMap<String, Object> result = new HashMap<>();
			result.put("id", document.getId());
			result.put("purchaseprice", document.getPurchaseprice());
			result.put("saleprice", document.getSaleprice());
			return Optional.of(result);
		}

		// Si no hay documentos o hay más de uno, devuelve vacío
		return Optional.empty();
	}
	
	public List<HistorialSolicitud> solitudesPorUsuario(String idUsuario) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id_usuario").is(idUsuario));

        return mongoTemplate.find(query, HistorialSolicitud.class, "historial_solicitud");
    }
	
	public boolean eliminarPorId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        return mongoTemplate.remove(query, "historial_solicitud").wasAcknowledged();
    }

}
