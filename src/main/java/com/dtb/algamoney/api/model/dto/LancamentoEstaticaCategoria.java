package com.dtb.algamoney.api.model.dto;

import java.math.BigDecimal;

import com.dtb.algamoney.api.model.entity.Categoria;

public class LancamentoEstaticaCategoria {
	private Categoria categoria;
	private BigDecimal total;
	public LancamentoEstaticaCategoria(Categoria categoria, BigDecimal total) {
		this.categoria = categoria;
		this.total = total;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
}
