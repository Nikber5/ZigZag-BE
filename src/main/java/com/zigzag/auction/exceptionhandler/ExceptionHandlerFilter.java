package com.zigzag.auction.exceptionhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zigzag.auction.dto.response.ErrorResponse;
import com.zigzag.auction.exception.InvalidJwtAuthenticationException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidJwtAuthenticationException e) {
            handleException(response, e, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            handleException(response, e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletResponse response, RuntimeException e, HttpStatus status)
            throws IOException {
        ErrorResponse errorResponse = ErrorResponse.of(e, status.value());
        response.setStatus(status.value());
        response.getWriter().write(convertObjectToJson(errorResponse));
        response.setHeader("Content-Type", "application/json");
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
