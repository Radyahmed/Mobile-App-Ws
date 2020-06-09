/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.exceptions;

/**
 *
 * @author Rady Ahmed
 */

public class AuthenticationException extends RuntimeException {

    private static final long serialVersionUID = -4627734137734311516L;

    public AuthenticationException(String string) {
        super(string);
    }

}
