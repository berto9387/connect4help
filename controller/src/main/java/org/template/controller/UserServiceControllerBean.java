package org.template.controller;

import org.template.interfaces.IUserServiceController;
import org.template.model.*;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
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

@Stateless(name = "UserServiceEJB")
public class UserServiceControllerBean implements IUserServiceController {


    public UserServiceControllerBean() {

    }

    @PersistenceContext(unitName = "MainPersistenceUnit")
    private EntityManager em;


    @Override
    public Collection<Service> getUserServices(int id, String role) {

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
            return (Collection<Service>) q.getResultList();
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

        try {
            em.merge(ser);
            return true;
        } catch (Exception e){
            return false;
        }


    }

    @Override
    public Service getUserService(Integer idService) {
        Service service = this.em.find(Service.class, idService);

        return service;

    }

    @Override
    public void deleteUserService(Integer idService) {
        em.remove(em.getReference(Service.class, idService));
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
