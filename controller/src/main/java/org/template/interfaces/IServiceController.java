package org.template.interfaces;

import org.template.model.Service;

import java.util.Collection;

public interface IServiceController {
    Collection<Service> getServices(String address, int radius);

    Boolean acceptService(Integer idService, Integer idUser);
}
