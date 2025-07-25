package org.ecommerce.ecommerceapi.security;

import java.io.IOException;
import java.util.Collections;

import org.ecommerce.ecommerceapi.providers.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    @Autowired
    JWTProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        logger.info("Authorization Header: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            logger.info("Extracted Token: {}", token);

            try {
                String subject = jwtProvider.validateToken(token);
                DecodedJWT decodedJWT = jwtProvider.getDecodedJWT(token);

                var rolesClaim = decodedJWT.getClaim("roles");
                logger.info("Roles Claim from JWT: {}", rolesClaim);

                var roles = rolesClaim != null ? rolesClaim.asList(String.class) : Collections.<String>emptyList();
                var authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();
                logger.info("Authorities added to Security Context: {}", authorities);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(subject, null,
                        authorities);

                SecurityContextHolder.getContext().setAuthentication(auth);

                request.setAttribute("cliente_id", subject);
            } catch (Exception e) {
                logger.error("Erro ao processar token JWT: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
