package org.template.controller;

import org.template.interfaces.IServiceController;
import org.template.model.Category;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;




@Stateless(name = "ServiceControllerEJB")
@Local(IServiceController.class)
public class ServiceControllerBean implements IServiceController {

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;

    public ServiceControllerBean() {
    }

    @Override
    //trova i servizi nel raggio di ....
    public Collection<Service> getServices() {


        Query q = this.em.createNamedQuery("Schedules");
        q.setParameter(1, 3.738076);
        q.setParameter(2, 15.497089);
        q.setParameter(3, 2000);
        try {
            return (Collection<Service>) q
                    .getResultList();
        } catch (NoResultException exc){
            return null;
        }
    }
}



