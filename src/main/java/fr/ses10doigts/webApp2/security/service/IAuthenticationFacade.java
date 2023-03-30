package fr.ses10doigts.webApp2.security.service;

import org.springframework.security.core.Authentication;

import fr.ses10doigts.webApp2.security.model.User;

public interface IAuthenticationFacade {
    Authentication getAuthentication();

    User getConnectedUser();
}
