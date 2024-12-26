package com.prestamype.reto_dev.service.interfaces;

import java.util.List;

import com.prestamype.reto_dev.persistence.entity.TasaCambio;
import com.prestamype.reto_dev.presentation.dto.TasaCambioDTO;

public interface ITasaCambioService {

	public TasaCambio crearTasaCambio(TasaCambioDTO tasaCambioDTO);
	
	public List<TasaCambio> getTasasCambio();
}
