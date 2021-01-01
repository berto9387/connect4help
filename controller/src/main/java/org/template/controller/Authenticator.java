package org.template.controller;

public interface Authenticator {
    public boolean isAuthTokenValid(String email,String authToken);
    public String login(String email, String password);
    public void logout(String authToken );
}