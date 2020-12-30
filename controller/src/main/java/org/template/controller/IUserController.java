package org.template.controller;

import org.template.model.Service;
import org.template.model.User;

/**
 * @author Victor Mezrin
 */

public interface IUserController {

    public void persistUser(String firstName, String lastName, String Role);
    public User retriveUser(String firstName );
    public Service retriveService(String firstName );
    public User loginUser(String email, String password);
    public String receiveRole();
}
