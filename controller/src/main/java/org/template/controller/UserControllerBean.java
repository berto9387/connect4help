package org.template.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;
import org.template.model.User;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Victor Mezrin
 */
@Stateless(name = "UserControllerEJB")
@Local(IUserController.class)
public class UserControllerBean implements IUserController {

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;


    @Override
    public void persistUser(String firstName, String lastName, String role) {
        User user=null;
        if (role.equals("P")){
            user = new PerformUser();
        } else if (role.equals("R")){
            user = new RequestUser();
        }
        user.setName(firstName);
        user.setSurname(lastName);
        this.em.persist(user);
    }



    @Override
    public User authenticate(String email, String password) {
        Query q = this.em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException exc){
            return null;
        }

    }




}
