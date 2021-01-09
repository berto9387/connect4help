package org.template.controller;

import org.template.interfaces.IUserServiceController;
import org.template.model.*;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Stateful(name = "UserServiceEJB")
public class UserServiceControllerBean implements IUserServiceController {

    private Collection<Service> services;

    public UserServiceControllerBean() {
        this.services=new ArrayList<>();
    }

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;


    @Override
    public Collection<Service> getUserServices(int id, String role) {
        if(services.size()!=0){
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
    public Boolean createService(String address, String details, int requestUser, String category, String startSlot,
                                 String endSlot, String expirationDate) {
        Category c = new Category();
        c.setName(category);
        RequestUser ru = new RequestUser();
        ru.setIdUser(requestUser);
        Service ser = new Service();
        ser.setAddress(address);
        ser.setDetails(details);
        ser.setCategory(c);
        ser.setRequestUser(ru);
        ser.setStartSlot(convertToTimestamp(startSlot));
        ser.setEndSlot(convertToTimestamp(endSlot));
        ser.setExpirationDate(convertToTimestamp(expirationDate));
        this.services.add(ser);
        try {
            em.merge(ser);
            return true;
        } catch (Exception e){
            this.services.remove(ser);
            return false;
        }


    }

    @Override
    public Service getUserService(Integer id, Integer idService, String role) {

        if(services.size()!=0){
            Optional<Service> matching=services.stream()
                    .filter(s -> s.getIdService() == idService)
                    .findFirst();
            if (matching.isPresent())
                return matching.get();
            return null;
        }
        this.getUserServices(id,role);
        return this.getUserService(id,idService, role);

    }

    @Override
    public Boolean deleteUserService(Integer id, Integer idService, String role) {
        if(this.services.size()!=0){
            Optional<Service> matching=this.services.stream()
                    .filter(s -> s.getIdService() == idService)
                    .findFirst();
            if (matching.isPresent()) {
                Service s = matching.get();
                if(s.getRequestUser().getIdUser()==id){
                    em.remove(em.getReference(Service.class, idService));
                    services.remove(s);
                    return true;
                }
            }
            return false;
        }
        this.getUserServices(id,role);
        return this.deleteUserService(id,idService, role);
    }

    private Timestamp convertToTimestamp(String localDate){
        Timestamp time = null;
      try {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
          Date parsedDate = dateFormat.parse(localDate);
          time = new java.sql.Timestamp(parsedDate.getTime());
      }catch(ParseException ex){
          ex.printStackTrace();
      }
      return time;
    }
}
