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
public class CouldNotUpdateRecordException extends RuntimeException {

    private static final long serialVersionUID = 8902642996711805623L;

    public CouldNotUpdateRecordException(String string) {
        super(string);
    }
}
