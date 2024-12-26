package com.prestamype.reto_dev.util;

import lombok.Data;

@Data
public class ExternalResponse {

	private boolean error;
    private String message;
    private int status;
    private ExternalData data;
}
