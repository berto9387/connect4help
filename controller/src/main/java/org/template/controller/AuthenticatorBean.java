package org.template.controller;

import org.template.model.User;

import javax.ejb.Local;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton(name = "AuthenticatorEJB")
@Local(Authenticator.class)
public class AuthenticatorBean implements Authenticator {


    // A service key storage which stores <token, email>
    private final Map<String, String> tokensStorage = new HashMap();

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;

    public AuthenticatorBean() {
    }

    @Override
    public boolean isAuthTokenValid(String email, String authToken) {
        String usernameMatch1 = tokensStorage.get( authToken );
        return usernameMatch1==email;
    }

    @Override
    public String login(String email, String password) {
        Query q = this.em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email and u.password = :password");
        q.setParameter("email", email);
        q.setParameter("password", password);
        User u=null;
        try {
            u = (User) q.getSingleResult();
        } catch (NoResultException exc){
            return "errore";
        }

        String authToken = UUID.randomUUID().toString();
        tokensStorage.put( authToken, email );
        return authToken;

    }

    @Override
    public void logout(String authToken) {

    }
}
