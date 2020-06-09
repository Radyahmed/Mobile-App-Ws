/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.service;

import com.apsdeveloperblog.apps.ws.exceptions.AuthenticationException;
import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;

/**
 *
 * @author Rady Ahmed
 */
public interface AuthenticationService {

    UserDTO authenticate(String userName, String password) throws AuthenticationException;

    String issueAccessToken(UserDTO userProfile) throws AuthenticationException;
    // Reset Access Token

    public void resetSecurityCridentials(String password, UserDTO userprofile);

}
