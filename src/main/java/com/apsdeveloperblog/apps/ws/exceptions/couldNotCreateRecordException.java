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
public class couldNotCreateRecordException extends RuntimeException {

    private static final long serialVersionUID = -6128793233913934295L;


    public couldNotCreateRecordException(String message) {
        super(message);
    }

}
