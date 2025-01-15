package com.agendamentohub.agendamento.security;

import com.agendamentohub.agendamento.model.User;
import com.agendamentohub.agendamento.service.UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

@Component
public class CustomOidcUserService implements org.springframework.security.oauth2.client.userinfo.OAuth2UserService<OidcUserRequest, OidcUser> {

    private final UserService userService;

    public CustomOidcUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        // 1. Carrega o usuário OIDC padrão
        OidcUserService delegate = new OidcUserService();
        OidcUser oidcUser = delegate.loadUser(userRequest);

        // 2. Extrai dados do usuário
        String email = oidcUser.getEmail();      // do token ID (scope=openid, email)
        String name = oidcUser.getFullName();    // full name (depende das claims)
        // Se quiser phoneNumber, nem sempre vem disponível. Depende dos scopes concedidos.

        // 3. Cria se não existir
        //    - Você já tem um método `createUserIfNotExists(name, email, phoneNumber?)` no UserService
        if (email != null) {
            User user = userService.createUserIfNotExists(name, email, null);
            // opcional: se quiser atualizar algo no user, roles, etc.
        }

        // 4. Retorna o OidcUser original para que o Spring Security prossiga
        return oidcUser;
    }
}
