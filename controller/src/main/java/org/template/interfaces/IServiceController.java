package org.template.interfaces;

import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

public interface IServiceController {
    public Collection<Service> getUserService(int id,String role);

    void createService(String address, String details, int requestUser, String category, Timestamp startSlot,
                       Timestamp endSlot, Timestamp expirationDate);
}
