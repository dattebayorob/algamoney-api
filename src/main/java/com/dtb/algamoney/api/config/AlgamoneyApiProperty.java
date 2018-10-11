package com.dtb.algamoney.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {
	
	private String origemPermitida = "http://localhost:8000";
	private boolean seguranca;
	
	public String getOrigemPermitida() {
		return origemPermitida;
	}

	public boolean getSeguranca() {
		return seguranca;
	}
}
