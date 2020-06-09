/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.ui.model.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Rady Ahmed
 */
@XmlRootElement
public class AuthenticationDetails {

    private String id;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
