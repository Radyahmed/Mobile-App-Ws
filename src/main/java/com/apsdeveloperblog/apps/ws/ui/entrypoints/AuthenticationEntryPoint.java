/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.ui.entrypoints;

import com.apsdeveloperblog.apps.ws.service.AuthenticationService;
import com.apsdeveloperblog.apps.ws.service.impl.AuthenticationServiceImpl;
import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;
import com.apsdeveloperblog.apps.ws.ui.model.request.LoginCredentials;
import com.apsdeveloperblog.apps.ws.ui.model.response.AuthenticationDetails;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Rady Ahmed
 */
@Path("/authentication")
public class AuthenticationEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials) {
        AuthenticationDetails returnvalue = new AuthenticationDetails();
        AuthenticationService authenticationService = new AuthenticationServiceImpl();
        UserDTO authenticatedUser = authenticationService.authenticate(loginCredentials.getUserName(), loginCredentials.getUserPassword());
        // Reset Access Token
        authenticationService.resetSecurityCridentials(loginCredentials.getUserPassword(), authenticatedUser);
        String accessToken = authenticationService.issueAccessToken(authenticatedUser);
        returnvalue.setId(authenticatedUser.getUserId());
        returnvalue.setToken(accessToken);
        return returnvalue;
    }
}
