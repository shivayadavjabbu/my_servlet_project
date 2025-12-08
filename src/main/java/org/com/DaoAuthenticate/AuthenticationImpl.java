package org.com.DaoAuthenticate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthenticationImpl implements AuthenticateInterface {

	 private final Connection connection;
	 
	 public AuthenticationImpl(Connection connection) {
	        this.connection = connection;
	 }
	 
	@Override
	public boolean authenticateUser(String username, String password) throws AuthenticateException {
        String query = "SELECT * FROM usertable WHERE username=? AND password=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new AuthenticateException("Error authenticating user", e);
        }
    }

	@Override
	public String getRole(String username) throws AuthenticateException {
        String query = "SELECT role FROM usertable WHERE username=?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            throw new AuthenticateException("Error retrieving user role", e);
        }
        return null;
    }

}
