package org.template.rest.model;

import org.template.model.Category;
import org.template.model.PerformUser;
import org.template.model.RequestUser;

public class ServiceResponse {
    private int idService;
    private String address;
    private String details;
    private String categoriaNome;
    private Boolean performed;
    private Boolean assistance;

    public ServiceResponse() {
    }

    public ServiceResponse(int idService, String address, String details, String categoriaNome, Boolean performed, Boolean assistance) {
        this.idService = idService;
        this.address = address;
        this.details = details;
        this.categoriaNome = categoriaNome;
        this.performed = performed;
        this.assistance = assistance;
    }
}
