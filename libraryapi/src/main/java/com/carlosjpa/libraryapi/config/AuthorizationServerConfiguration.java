package com.carlosjpa.libraryapi.config;


import com.carlosjpa.libraryapi.security.CustomAuthentication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfiguration {

    @Bean
    @Order(1) // Define como sendo o primeiro da chain do security
    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception  {

        // Aplica as configurações
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        // Pegando a configuração
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults()); // Plugin para com o token em maos pegar o usuário que está autenticado

        // Usa o token jwt e valida se foi gerado por este resource server
        http.oauth2ResourceServer(oauth2Rs -> oauth2Rs.jwt(Customizer.withDefaults()));

        http.formLogin(configurer -> configurer.loginPage("/login"));

        return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }


    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder().
                accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                // acess token : Token utilizado nas requisições
                .accessTokenTimeToLive(Duration.ofMinutes(60))
                // Token para renovar o acess_token
                .refreshTokenTimeToLive(Duration.ofMinutes(90))
                .build();
    }

    @Bean
    public ClientSettings clientSettings() {
        return ClientSettings.builder().
                requireAuthorizationConsent(false)
                .build();
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                // obter token
                .tokenEndpoint("/oauth2/token")
                // Pegar informações do token
                .tokenIntrospectionEndpoint("/oauth2/instrospect")
                // Revogar o token
                .tokenRevocationEndpoint("/oauth2/revoke")
                // Authorization endPoint, acessar a login page
                .authorizationEndpoint("/oauth2/authorize")
                // informações usuario Open id connect, para solicitar os dados expostos do usuário
                .oidcUserInfoEndpoint("/oauth2/userinfo")
                // Obter a chave pública para verificar a assinatura do token
                .jwkSetEndpoint("/oauth2/jwks")
                // logou
                .oidcLogoutEndpoint("/oauth2/logout")
                .build();
    }
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> tokenCustomizer(){
        // Context passado pelo authorization server
        return context -> {
            var principal = context.getPrincipal();
            if (principal instanceof CustomAuthentication authentication) {
                // Ver  o tipo de token
                OAuth2TokenType tipoToken = context.getTokenType();
                // Verifica se é um acess token
                if(OAuth2TokenType.ACCESS_TOKEN.equals(tipoToken)) {
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    List<String> list = authorities.stream().map(GrantedAuthority::getAuthority).toList();
                    context.getClaims()
                            .claim("authorits", authorities)
                            .claim("email", authentication.getUsuario().getEmail()).build();

                }
            }
        };
    }
}
