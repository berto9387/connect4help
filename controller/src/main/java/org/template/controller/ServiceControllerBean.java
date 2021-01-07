package org.template.controller;

import org.template.interfaces.IServiceController;
import org.template.model.Service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;

@Stateless(name = "ServiceControllerEJB")
@Local(IServiceController.class)
public class ServiceControllerBean implements IServiceController {

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;

    public ServiceControllerBean() {
    }

    @Override
    public Collection<Service> getServices() {
        Query q=null;
        q = this.em.createQuery(
                "SELECT s FROM Service s where s.performerUser=null");

        try {
            return (Collection<Service>) q.getResultList();
        } catch (NoResultException exc){
            return null;
        }
    }
}
