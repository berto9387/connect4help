package org.template.interfaces;

import org.template.model.Service;

import java.util.Collection;

public interface IUserServiceController {
    public Collection<Service> getUserServices(int id,String role);

    Boolean createService(String address, String details, int requestUser, String category, String startSlot, String endSlot, String expirationDate);

    Service getUserService(Integer id, Integer idService, String role);

    Boolean deleteUserService(Integer id, Integer idService, String role);
}