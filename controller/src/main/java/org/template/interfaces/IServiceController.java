package org.template.interfaces;

import org.template.model.Service;

import java.util.Collection;
import java.util.Date;

public interface IServiceController {
    public Collection<Service> getUserService(int id,String role);

    void createService(String address, String details, int requestUser, String category, Date startSlot, Date endSlot, Date expirationDate);
}
