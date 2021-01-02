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

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public void setCategoriaNome(String categoriaNome) {
        this.categoriaNome = categoriaNome;
    }

    public Boolean getPerformed() {
        return performed;
    }

    public void setPerformed(Boolean performed) {
        this.performed = performed;
    }

    public Boolean getAssistance() {
        return assistance;
    }

    public void setAssistance(Boolean assistance) {
        this.assistance = assistance;
    }
}
