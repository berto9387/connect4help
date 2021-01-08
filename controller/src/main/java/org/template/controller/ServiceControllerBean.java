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
        String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(?1)) * cos(radians(s.Latitude)) *" +
                " cos(radians(s.Longitude) - radians(?2)) + sin(radians(?1)) * sin(radians(s.latitude))))";

        String query="\n" +
                "    SELECT s.*\n" +
                "        FROM service s\n" +
                "        where "+HAVERSINE_FORMULA+"< ?3\n" ;
        Query q=null;
        q = this.em.createNativeQuery(query);
        q.setParameter(1, 3.738076);
        q.setParameter(2, 15.497089);
        q.setParameter(3, 2000);
        @SqlResultSetMapping(
                name="groupDetailsMapping",
                classes={
                        @ConstructorResult(
                                targetClass=GroupDetails.class,
                                columns={
                                        @ColumnResult(name="GROUP_ID"),
                                        @ColumnResult(name="USER_ID")
                                }
                        )
                }
        )
        try {
            return (Collection<Service>) q
                    .getResultList();
        } catch (NoResultException exc){
            return null;
        }
    }
}



