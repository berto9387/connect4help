package org.template.interfaces;

import org.template.model.Service;

import java.util.Collection;

public interface IServiceController {
    public Collection<Service> getUserService(int id,String role);

    Boolean createService(String address, String details, int requestUser, String category, String startSlot, String endSlot, String expirationDate);
}
