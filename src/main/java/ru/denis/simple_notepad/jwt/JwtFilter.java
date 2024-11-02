package ru.denis.simple_notepad.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.denis.simple_notepad.exception.JwtAuthenticationException;

import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider provider;

    public JwtFilter(JwtProvider provider) {
        this.provider = provider;
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = provider.resolveToken((HttpServletRequest) servletRequest);

        try {
            if(token != null && provider.validateToken(token)) {
                Authentication authentication = provider.getAuthentication(token);

                if(authentication != null) {
                    System.out.println("its good");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch(AuthenticationException e) {
            SecurityContextHolder.clearContext();
            e.printStackTrace();
            throw  new JwtAuthenticationException(HttpStatus.UNAUTHORIZED);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
