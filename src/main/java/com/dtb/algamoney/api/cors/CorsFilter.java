package com.dtb.algamoney.api.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	private String originAllowed; //ToDo: Alterar em produção
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		resp.setHeader("Acces-Control-Allow-Origin", originAllowed);
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		if (req.getMethod().equals("OPTIONS") && req.getHeader("Origin").equals(originAllowed)) {
			resp.setHeader("Acces-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTIONS");
			resp.setHeader("Acces-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Acces-Control-Max-Age", "3600");
			
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
