package com.prestamype.reto_dev.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.prestamype.reto_dev.util.ExternalResponse;

import reactor.core.publisher.Mono;

@Service
public class ExternalService {

	@Autowired
    private WebClient webClient;

    private static final String SERVICE_URL = "https://api.test.cambioseguro.com/api/v1.1/config/rates";

    public ExternalResponse fetchData() {
        ExternalResponse response = webClient.get()
            .uri(SERVICE_URL)
            .retrieve()
            .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new RuntimeException("Error en el servicio externo")))
            .bodyToMono(ExternalResponse.class)
            .block();

        if (response != null && !response.isError() && "Ok".equals(response.getMessage()) && response.getStatus() == 200) {
            return response;
        }
        throw new RuntimeException("Respuesta inválida del servicio externo");
    }
}
