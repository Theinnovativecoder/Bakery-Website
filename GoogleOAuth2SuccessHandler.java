package com.sheryians.major.configuratiion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import org.springframework.stereotype.Component;

import com.sheryians.major.repository.RoleRepository;
import com.sheryians.major.repository.UserRepository;
import com.sheryians.major.model.User;
import com.sheryians.major.model.Role;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, 
            HttpServletResponse httpServletResponse, Authentication authentication) 
            throws IOException, ServletException {
        
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttributes().get("email").toString();

        if (userRepository.findUserByEmail(email).isPresent()) {
           
        } else {
            // User does not exist, create a new user
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
            user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
            user.setEmail(email);

            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get()); // Assuming 2 is the default role ID
            user.setRoles(roles);

            userRepository.save(user);
        }
            // Redirect user after successful registration
            redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse, "/");
        }
    }

