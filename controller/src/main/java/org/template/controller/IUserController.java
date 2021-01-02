package org.template.controller;

import org.template.model.PerformUser;
import org.template.model.Service;
import org.template.model.User;

import java.sql.Date;
import java.util.List;

/**
 * @author Victor Mezrin
 */

public interface IUserController {

    public void createUser(String firstName, String lastName, String password, String email, String address,
                            Date dateOfBirth,String role, String telephone);
    public void deleteUser(int id);
    public User authenticate(String email, String password) ;
    public User getUser(int id);
    public PerformUser getPerformUser(int id);

    public List<User> findAllUser() ;
}
