package com.apsdeveloperblog.apps.ws.service.impl;

import com.apsdeveloperblog.apps.ws.exceptions.CouldNotDeleteRecordException;
import com.apsdeveloperblog.apps.ws.exceptions.CouldNotUpdateRecordException;
import com.apsdeveloperblog.apps.ws.exceptions.NoRecordFoundException;
import com.apsdeveloperblog.apps.ws.exceptions.couldNotCreateRecordException;
import com.apsdeveloperblog.apps.ws.io.dao.DAO;
import com.apsdeveloperblog.apps.ws.io.dao.impl.MySQLDAO;
import com.apsdeveloperblog.apps.ws.service.UserService;
import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;
import com.apsdeveloperblog.apps.ws.ui.model.response.ErrorMessages;
import com.apsdeveloperblog.apps.ws.utils.UserProfileUtils;
import java.util.List;

public class UserServiceImpl implements UserService {

    DAO database;
    UserProfileUtils userProfileUtils = new UserProfileUtils();

    public UserServiceImpl() {
        database = new MySQLDAO();
    }

    @Override
    public UserDTO createuser(UserDTO user) {
        UserDTO returnValue = null;

        //validate the required field
        userProfileUtils.validateRequiredFields(user);
        // check if user already exists
        UserDTO existingUser = this.getUserByUserName(user.getEmail());
        if (existingUser != null) {
            throw new couldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }
        //generate secure public user id
        String userId = userProfileUtils.generateUserId(30);
        user.setUserId(userId);

        //generate salt
        String salt = userProfileUtils.getSalt(30);
        //generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);
        //record data into database
        returnValue = this.saveUser(user);
        return returnValue;
    }

    @Override
    public UserDTO getUserByUserName(String userName) {
        UserDTO userDto = null;

        if (userName == null || userName.isEmpty()) {
            return userDto;
        }

        // Connect to database 
        try {
            this.database.openConnection();
            userDto = this.database.getUSerByUserName(userName);
        } finally {
            this.database.closeConnection();
        }

        return userDto;
    }

    private UserDTO saveUser(UserDTO user) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);
        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }

    @Override
    public UserDTO getUser(String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.No_RECORD_FOUND.getErrorMessage());
        } finally {
            this.database.closeConnection();
        }
        return returnValue;
    }

    @Override
    public List<UserDTO> getUsers(int start, int limit) {
        List<UserDTO> users = null;

        // Get users from database
        try {
            this.database.openConnection();
            users = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }

        return users;
    }

    @Override
    public void updateUserDetails(UserDTO userDetails) {
        try {
            // Connect to database 
            this.database.openConnection();
            // Update User Details
            this.database.updateUser(userDetails);

        } catch (Exception ex) {
            throw new CouldNotUpdateRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }

    @Override
    public void deleteUser(UserDTO userDto) {
        try {
            this.database.openConnection();
            this.database.deleteUser(userDto);
        } catch (Exception ex) {
            throw new CouldNotDeleteRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }

        // Verify that user is deleted
        try {
            userDto = getUser(userDto.getUserId());
        } catch (NoRecordFoundException ex) {
            userDto = null;
        }

        if (userDto != null) {
            throw new CouldNotDeleteRecordException(
                    ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessage());
        }
    }

}
