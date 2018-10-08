package com.dtb.algamoney.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodarSenha {
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("mobile"));
	}
}
