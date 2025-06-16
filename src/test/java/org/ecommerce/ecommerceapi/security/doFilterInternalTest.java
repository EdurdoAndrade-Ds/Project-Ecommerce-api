package org.ecommerce.ecommerceapi.security;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.ecommerce.ecommerceapi.providers.JWTProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import jakarta.servlet.FilterChain;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecurityFilterTest {

    @InjectMocks
    private SecurityFilter securityFilter;

    @Mock
    private JWTProvider jwtProvider;

    @Mock
    private DecodedJWT decodedJWT;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Claim claim;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.setContext(new SecurityContextImpl()); // Limpa o contexto antes de cada teste
    }

    @Test
    void deveSetarAutenticacaoQuandoTokenValido() throws Exception {
        String token = "tokenValido";
        String subject = "usuario123";

        request.addHeader("Authorization", "Bearer " + token);

        when(jwtProvider.validateToken(token)).thenReturn(subject);
        when(jwtProvider.getDecodedJWT(token)).thenReturn(decodedJWT);
        when(decodedJWT.getClaim("roles")).thenReturn(claim);
        when(claim.asList(String.class)).thenReturn(List.of("CLIENTE"));

        securityFilter.doFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(subject, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertEquals(1, SecurityContextHolder.getContext().getAuthentication().getAuthorities().size());
        assertTrue(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE")));
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deveRetornar401SeTokenInvalido() throws Exception {
        String token = "tokenInvalido";
        request.addHeader("Authorization", "Bearer " + token);

        when(jwtProvider.validateToken(token)).thenThrow(new RuntimeException("Token inválido"));

        securityFilter.doFilterInternal(request, response, filterChain);

        assertEquals(401, response.getStatus());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain, never()).doFilter(request, response);
    }
}
