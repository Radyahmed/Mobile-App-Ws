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
public class NoRecordFoundException extends RuntimeException {

    private static final long serialVersionUID = 3744365404068163424L;

    public NoRecordFoundException(String string) {
        super(string);
    }

}
