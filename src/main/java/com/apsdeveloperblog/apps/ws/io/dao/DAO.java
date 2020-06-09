/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.io.dao;

import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;
import java.util.List;

/**
 *
 * @author Rady Ahmed
 */
public interface DAO {

    void openConnection();

    UserDTO getUSerByUserName(String userName);

    UserDTO saveUser(UserDTO user);

    void updateUser(UserDTO userProfile);

    void closeConnection();

    UserDTO getUser(String id);

    List<UserDTO> getUsers(int start, int limit);

    void deleteUser(UserDTO userPofile);
}
