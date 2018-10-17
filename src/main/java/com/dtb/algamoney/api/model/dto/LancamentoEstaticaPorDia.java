package com.dtb.algamoney.api.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.dtb.algamoney.api.model.enums.Tipo;

public class LancamentoEstaticaPorDia {
	private Tipo tipo;
	private LocalDate dia;
	private BigDecimal total;
	public LancamentoEstaticaPorDia(Tipo tipo, LocalDate dia, BigDecimal total) {
		this.tipo = tipo;
		this.dia = dia;
		this.total = total;
	}
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	public LocalDate getDia() {
		return dia;
	}
	public void setDia(LocalDate dia) {
		this.dia = dia;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
