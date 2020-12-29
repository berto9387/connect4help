package org.template.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;
import org.template.model.User;

import javax.annotation.Resource;
import javax.ejb.Local;
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
    private Logger LOG = LoggerFactory.getLogger(UserControllerBean.class);

    @Resource(name = "NamePrefix")
    private String NamePrefix;


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
    public User retriveUser(String name) {
        Query q = this.em.createQuery(
                "SELECT u FROM User u WHERE u.name = :name");
        q.setParameter("name", name);
        try {
            return (User) q.getSingleResult();
        } catch (NoResultException exc){
            return null;
        }

    }

    @Override
    public Service retriveService(String firstName) {
        return null;
    }


}
