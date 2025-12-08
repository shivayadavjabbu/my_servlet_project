package org.com.DaoAuthenticate;

public interface AuthenticateInterface {
	
    boolean authenticateUser(String username, String password) throws AuthenticateException;

    String getRole(String username) throws AuthenticateException;
}
