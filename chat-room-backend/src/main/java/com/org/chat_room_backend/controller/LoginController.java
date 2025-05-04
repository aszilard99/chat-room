package com.org.chat_room_backend.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    AuthenticationManager authenticationManager;

    SecurityContextRepository securityContextRepository;

    public LoginController(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest, HttpServletRequest request,
                      HttpServletResponse response){
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username, loginRequest.password);
        Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);

        saveAuthenticationIntoHttpSession(request, response, authenticationResponse);
    }

    private void saveAuthenticationIntoHttpSession(HttpServletRequest request, HttpServletResponse response, Authentication authenticationResponse) {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authenticationResponse);

        securityContextRepository.saveContext(securityContext, request, response);
    }

    private record LoginRequest(String username, String password){}
}
