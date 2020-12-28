package org.template.controller;

import org.template.model.Service;
import org.template.model.User;

/**
 * @author Victor Mezrin
 */

public interface IUserController {

    public void persistUser(String firstName, String lastName);
    public User retriveUser(String firstName );
    public Service retriveService(String firstName );
}
