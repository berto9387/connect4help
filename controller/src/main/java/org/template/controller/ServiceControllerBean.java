package org.template.controller;

import org.template.interfaces.IServiceController;
import org.template.model.Service;
import org.template.util.OpenStreetMapUtils;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.Collection;
import java.util.Map;


@Stateless(name = "ServiceControllerEJB")
@Local(IServiceController.class)
public class ServiceControllerBean implements IServiceController {

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;

    public ServiceControllerBean() {
    }

    @Override
    //trova i servizi nel raggio di ....
    public Collection<Service> getServices(String address, int radius) {

        Map<String, Double> coords;
        coords = OpenStreetMapUtils.getInstance().getCoordinates(address);
        Query q = this.em.createNamedQuery("Schedules");
        q.setParameter(1, coords.get("lat"));
        q.setParameter(2, coords.get("lon"));
        q.setParameter(3, radius);
        try {
            return (Collection<Service>) q
                    .getResultList();
        } catch (NoResultException exc){
            return null;
        }
    }
}



