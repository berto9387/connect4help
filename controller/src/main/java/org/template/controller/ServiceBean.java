package org.template.controller;

import org.template.interfaces.IServiceController;
import org.template.model.*;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateful(name = "ServiceEJB")
public class ServiceBean implements IServiceController {

    private Collection<Service> services;

    public ServiceBean() {
        services=new ArrayList<>();
    }

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;


    @Override
    public Collection<Service> getUserService(int id, String role) {
        if(!services.isEmpty()){
            return services;
        }
        Query q=null;
        if(role.contains("P")){
            q = this.em.createQuery(
                    "SELECT s FROM Service s where s.performerUser.idUser=:id");
        } else if (role.contains("R")){
            q = this.em.createQuery(
                    "SELECT s FROM Service s where s.requestUser.idUser=:id");
        }
        q.setParameter("id", id);
        try {
            this.services = (Collection<Service>) q.getResultList();
            return this.services;
        } catch (NoResultException exc){
            return null;
        }
    }

    @Override
    public void createService(String address, String details, int requestUser, String category, Timestamp startSlot,
                              Timestamp endSlot, Timestamp expirationDate) {
        Category c = new Category();
        c.setName(category);
        Query q = this.em.createQuery("SELECT u FROM RequestUser u where u.idUser=:id");

        q.setParameter("id", requestUser);
        RequestUser u=null;
        try {
            u=(RequestUser) q.getSingleResult();
        } catch (NoResultException exc){
            //gestire caso u = null
        }
        Service ser = new Service();
        ser.setAddress(address);
        ser.setDetails(details);
        ser.setCategory(c);
        ser.setStartSlot(startSlot);
        ser.setEndSlot(endSlot);
        ser.setExpirationDate(expirationDate);
        services.add(ser);
        em.merge(ser);

    }
}
