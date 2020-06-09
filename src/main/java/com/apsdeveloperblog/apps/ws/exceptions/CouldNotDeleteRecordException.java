/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.exceptions;

/**
 *
 * @author radya
 */
public class CouldNotDeleteRecordException extends RuntimeException {

    private static final long serialVersionUID = 2164033900460813697L;

    public CouldNotDeleteRecordException(String message) {
        super(message);
    }

}
