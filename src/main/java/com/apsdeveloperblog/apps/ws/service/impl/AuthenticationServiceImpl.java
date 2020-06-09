/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apsdeveloperblog.apps.ws.service.impl;

import com.apsdeveloperblog.apps.ws.exceptions.AuthenticationException;
import com.apsdeveloperblog.apps.ws.io.dao.DAO;
import com.apsdeveloperblog.apps.ws.io.dao.impl.MySQLDAO;
import com.apsdeveloperblog.apps.ws.service.AuthenticationService;
import com.apsdeveloperblog.apps.ws.service.UserService;
import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;
import com.apsdeveloperblog.apps.ws.ui.model.response.ErrorMessages;
import com.apsdeveloperblog.apps.ws.utils.UserProfileUtils;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rady Ahmed
 */
public class AuthenticationServiceImpl implements AuthenticationService {

    DAO database;

    @Override
    public UserDTO authenticate(String userName, String password) throws AuthenticationException {
        UserService userService = new UserServiceImpl();
        UserDTO storedUser = userService.getUserByUserName(userName);
        if (storedUser == null) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }
        String encryptedPassword = null;
        encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storedUser.getSalt());
        boolean authenticated = false;
        if (encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storedUser.getEncryptedPassword())) {
            if (userName != null && userName.equalsIgnoreCase(storedUser.getEmail())) {
                authenticated = true;
            }
        }

        if (!authenticated) {
            throw new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
        }
        return storedUser;
    }

    @Override
    public String issueAccessToken(UserDTO userProfile) throws AuthenticationException {
        String returnValue = null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId() + newSaltAsPostfix;

        byte[] encryptedAccessToken = null;
        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthenticationServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }

        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);
        updateUserProfile(userProfile);

        return returnValue;

    }

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MySQLDAO();
        try {
            database.openConnection();
            database.updateUser(userProfile);
        } finally {
            database.closeConnection();
        }
    }

    @Override
    public void resetSecurityCridentials(String password, UserDTO userprofile) {
        // generate new salt
        UserProfileUtils userProfileUtils = new UserProfileUtils();
        String salt = userProfileUtils.getSalt(30);
        // generate new password
        String securePassword = userProfileUtils.generateSecurePassword(password, salt);
        userprofile.setSalt(salt);
        userprofile.setEncryptedPassword(securePassword);
        // update user profile
        updateUserProfile(userprofile);

    }
}
