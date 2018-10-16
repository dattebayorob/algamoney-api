package com.dtb.algamoney.api.model.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UsuarioSistema extends User{
	/**
	 * 
	 */
	private Usuario usuario;
	private static final long serialVersionUID = -8331543433855844642L;

	public UsuarioSistema(String username, String password, Collection<? extends GrantedAuthority> authorities, Usuario usuario) {
		super(username, password, authorities);
		this.usuario = usuario;
	}


	public Usuario getUsuario() {
		return usuario;
	}
	
}
