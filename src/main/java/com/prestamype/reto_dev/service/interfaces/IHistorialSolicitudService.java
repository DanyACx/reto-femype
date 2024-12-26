package com.prestamype.reto_dev.service.interfaces;

import java.util.List;

import com.prestamype.reto_dev.persistence.entity.HistorialSolicitud;
import com.prestamype.reto_dev.presentation.dto.HistorialSolicitudDTO;
import com.prestamype.reto_dev.util.ExternalResponse;

public interface IHistorialSolicitudService {

	public ExternalResponse getAPIExterna();
	public HistorialSolicitud insertarHistorial(HistorialSolicitudDTO historialSolicitudDTO);
	public void insertar(HistorialSolicitudDTO historialSolicitudDTO);
	public List<HistorialSolicitud> solitudesPorUsuario(String idUsuario);
	public boolean eliminarPorId(String id);
}
