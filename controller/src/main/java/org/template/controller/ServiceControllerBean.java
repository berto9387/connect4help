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
    //trova i servizi nel raggio di ....
    public Collection<Service> getServices() {
        String query="\n" +
                "    SELECT s.*,6371 * acos(cos(radians(?1)) * cos(radians(s.Latitude)) * cos(radians(s.Longitude) -radians(?2)) +sin(radians(?1) * sin(radians(s.Latitude )))) AS distance\n" +
                "        FROM service s\n" +
                "        HAVING distance < 2000\n" +
                "        ORDER BY distance LIMIT 0, 20;\n";
        String query_1="select s.*,(6371 * acos(cos(radians(?1)) * cos(radians(s.Latitude)) * cos(radians(s.Longitude) -radians(?2)) +sin(radians(?1) * sin(radians(s.Latitude )))) AS distance) from Service s ";
        Query q=null;
        q= this.em.createQuery("select new Service(Service , (6371 * acos(cos(radians(lat= :lat)) * cos(radians(s.latitude)) * cos(radians(s.longitude) -radians(long= :long)) +sin(radians(lat= :lat) * sin(radians(s.Latitude )))))AS distance) from Service  having distance < 2000");
        q = this.em.createNativeQuery(query);
        q.setParameter("lat", 3.738076);
        q.setParameter("long", 15.497089);
        try {
            return (Collection<Service>) q
                    .getResultList();
        } catch (NoResultException exc){
            return null;
        }
    }
}



