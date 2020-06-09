package com.apsdeveloperblog.apps.ws.service;

import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;
import java.util.List;

public interface UserService {

    UserDTO createuser(UserDTO user);

    UserDTO getUser(String id);

    UserDTO getUserByUserName(String userName);

    List<UserDTO> getUsers(int start, int limit);

    void updateUserDetails(UserDTO userDetails);

    void deleteUser(UserDTO userDto);
}
