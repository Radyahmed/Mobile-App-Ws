package com.apsdeveloperblog.apps.ws.filters;

import com.apsdeveloperblog.apps.ws.annotations.Secured;
import com.apsdeveloperblog.apps.ws.exceptions.AuthenticationException;
import com.apsdeveloperblog.apps.ws.service.UserService;
import com.apsdeveloperblog.apps.ws.service.impl.UserServiceImpl;
import com.apsdeveloperblog.apps.ws.shared.dto.UserDTO;
import com.apsdeveloperblog.apps.ws.utils.UserProfileUtils;
import java.io.IOException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Extract Authorization header details
        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Rady")) {
            throw new AuthenticationException("Authorization header must be provided");
        }

        // Extract the token
        String token = authorizationHeader.substring("Rady".length()).trim();

        // Extract user id
        String userId = requestContext.getUriInfo().getPathParameters().getFirst("id");

        validateToken(token, userId);

    }
    private void validateToken(String token, String userId) throws AuthenticationException {
      
        // Get user profile details
        UserService usersService = new UserServiceImpl();
        UserDTO userProfile = usersService.getUser(userId);
        
        // Asseble Access token using two parts. One from DB and one from http request.
        String completeToken = userProfile.getToken()+token;
        
        // Create Access token material out of the useId received and salt kept database
        String securePassword = userProfile.getEncryptedPassword();
        String salt = userProfile.getSalt();
        String accessTokenMaterial = userId + salt;
        byte[] encryptedAccessToken = null;
       
        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(securePassword, accessTokenMaterial);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(AuthenticationFilter.class.getName()).log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Faled to issue secure access token");
        }
        
        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);
       
        // Compare two access tokens 
        if (!encryptedAccessTokenBase64Encoded.equalsIgnoreCase(completeToken)) {
            throw new AuthenticationException("Authorization token did not match");
        }
    }
}
