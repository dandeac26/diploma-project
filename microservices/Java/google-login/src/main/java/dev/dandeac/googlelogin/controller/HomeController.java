package dev.dandeac.googlelogin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "Hello World!";
    }

    @GetMapping("/secured")
    public String secured() {
        return "Hello Secured!";
    }

//    @GetMapping("/securedId")
//    public String securedEndpoint(Authentication authentication) {
//        // Access the authentication object
//        // You can also use SecurityContextHolder.getContext().getAuthentication()
//        // but injecting it directly is more recommended
//        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Retrieve the client ID of the signed-in user
//        String clientId = null;
//        if (authentication != null && authentication.isAuthenticated()) {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//            OAuth2AuthorizedClient authorizedClient =
//                    oauthToken.getAuthorizedClientRegistrationId();
//
//            clientId = authorizedClient.getClientRegistration().getClientId();
//        }
//        return "Client ID: " + clientId;
//    }
}
