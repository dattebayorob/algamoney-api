package com.dtb.algamoney.api.service;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BasicoService <T,F>{
	public T atualizar(T t, Long id);
	public Optional<T> buscarPeloId(Long id);
	public Page<T> filtrar(F filter,Pageable pageAble);
	public T adicionar(T t,HttpServletResponse response);
	public void removerPeloId(Long id);
	
}
