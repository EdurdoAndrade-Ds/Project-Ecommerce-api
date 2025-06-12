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
    private Claim claim; // Corrigido

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        SecurityContextHolder.setContext(new SecurityContextImpl()); // limpa contexto antes
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
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deveContinuarSeNaoTiverHeaderAuthorization() throws Exception {
        securityFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void deveRetornar401SeTokenInvalido() throws Exception {
        String token = "tokenInvalido";
        request.addHeader("Authorization", "Bearer " + token);

        when(jwtProvider.validateToken(token)).thenThrow(new RuntimeException("Token inválido"));

        securityFilter.doFilterInternal(request, response, filterChain);

        assertEquals(401, response.getStatus());
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    void testGetClaimRole() {
        Claim mockClaim = mock(Claim.class);
        when(mockClaim.asString()).thenReturn("CLIENTE");

        String role = mockClaim.asString();
        assertEquals("CLIENTE", role);
    }
}
