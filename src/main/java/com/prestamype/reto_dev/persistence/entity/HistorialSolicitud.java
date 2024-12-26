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
@Document(collection = "historial_solicitud")
public class HistorialSolicitud {

	@Id
	@Field("_id")
	private String id;
	@Field("tipo_de_cambio")
	private String tipodecambio;
	@Field("monto_enviar")
	private double montoenviar;
	@Field("monto_recibir")
	private double montorecibir;
	@Field("id_usuario")
	private String idusuario;
	@Field("tasa_de_cambio")
	private TasaCambioHistorial tasacambiohistorial;
}
