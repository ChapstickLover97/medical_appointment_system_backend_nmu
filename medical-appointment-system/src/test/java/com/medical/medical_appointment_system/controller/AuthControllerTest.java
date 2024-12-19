package com.medical.medical_appointment_system.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.servlet.http.HttpServletRequest;

class AuthControllerTest {

    @Mock
    private ClientRegistrationRepository clientRegistrationRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private OAuth2AuthorizedClient authorizedClient;

    @InjectMocks
    private AuthController authController;

    private OAuth2User mockUser;
    private ClientRegistration mockRegistration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock ClientRegistration
        mockRegistration = mock(ClientRegistration.class);
        when(clientRegistrationRepository.findByRegistrationId("okta")).thenReturn(mockRegistration);
        ClientRegistration.ProviderDetails mockProviderDetails = mock(ClientRegistration.ProviderDetails.class);
when(mockRegistration.getProviderDetails()).thenReturn(mockProviderDetails);
when(mockProviderDetails.getConfigurationMetadata())
        .thenReturn(Map.of("end_session_endpoint", "http://logout-endpoint.com"));

        // Mock OAuth2User
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("groups", List.of("Admin", "Patients"));
        attributes.put("name", "Mock User");
        mockUser = new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                attributes,
                "name"
        );
    }

    @Test
    void testRedirectToFrontend_Unauthenticated() {
        String response = authController.redirectToFrontend(null);

        assertThat(response).isEqualTo("redirect:http://localhost:3000/");
    }

    @Test
    void testRedirectToFrontend_AdminGroup() {
        when(mockUser.getAttributes()).thenReturn(Map.of("groups", List.of("Admin")));

        String response = authController.redirectToFrontend(mockUser);

        assertThat(response).isEqualTo("redirect:http://localhost:3000/admin");
    }

    @Test
    void testRedirectToFrontend_PatientGroup() {
        when(mockUser.getAttributes()).thenReturn(Map.of("groups", List.of("Patients")));

        String response = authController.redirectToFrontend(mockUser);

        assertThat(response).isEqualTo("redirect:http://localhost:3000/dashboard");
    }

    @Test
    void testGetUser() {
        ResponseEntity<?> response = authController.getUser(mockUser);

        assertThat(response.getBody()).isNotNull();
        assertThat(((Map<String, Object>) response.getBody()).get("name")).isEqualTo("Mock User");
    }

    @Test
    void testOauthUserInfo() {
        when(authorizedClient.getClientRegistration()).thenReturn(mockRegistration);
        when(mockRegistration.getClientName()).thenReturn("Okta");

        String response = authController.oauthUserInfo(authorizedClient, mockUser);

        assertThat(response).contains("User Name: Mock User");
        assertThat(response).contains("Client Name: Okta");
    }

    @Test
    void testLogout() {
        OidcIdToken mockIdToken = mock(OidcIdToken.class);
        when(mockIdToken.getTokenValue()).thenReturn("mock-id-token");
        when(mockRegistration.getProviderDetails().getConfigurationMetadata())
                .thenReturn(Map.of("end_session_endpoint", "http://logout-endpoint.com"));

        ResponseEntity<?> response = authController.logout(request, mockIdToken);

        assertThat(response.getBody()).isNotNull();
        assertThat(((Map<String, String>) response.getBody()).get("logoutUrl")).isEqualTo("http://logout-endpoint.com");
        assertThat(((Map<String, String>) response.getBody()).get("idToken")).isEqualTo("mock-id-token");

        verify(request, times(1)).getSession(false);
    }
}
