package com.example.Examenfinal.Config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/countries/*")  // Aplica el filtro solo a los endpoints /api/usuarios/*
public class ApiKeyFilter implements Filter {

    @Value("${API_KEY_HEADER:admin}")  // Clave del encabezado (por defecto "user")
    private String apiKeyHeader;

    @Value("${API_KEY_VALUE:123}")  // Valor de la clave API (por defecto "123")
    private String validApiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Leer el encabezado especificado
        String apiKey = httpRequest.getHeader(apiKeyHeader);

        // Verificar si la clave es válida
        if (apiKey != null && apiKey.equals(validApiKey)) {
            // Si la clave es válida, continúa con la solicitud
            chain.doFilter(request, response);
        } else {
            // Si la clave no es válida, devuelve un error 401
            httpResponse.setContentType("text/plain");
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Código 401
            httpResponse.getWriter().write("Usuario no autorizado");
        }
    }
}