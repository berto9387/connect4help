package org.template.controller;

import org.template.model.Service;
import org.template.model.User;

/**
 * @author Victor Mezrin
 */

public interface IUserController {

    public void persistUser(String firstName, String lastName, String Role);
    public User authenticate(String email, String password) ;
}
