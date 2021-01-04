package org.template.rest.model;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.template.rest.util.CustomDateSerializer;

import java.time.LocalDate;
import java.util.Date;

public class ServiceResponse {
    private int idService;
    private String address;
    private String details;
    private int requestUser;
    private String category;
    private int performerUser;
    private Boolean performed;
    private Boolean assistance;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startSlot;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date endSlot;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date expirationDate;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date insertionDate;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date acceptanceDate;
    //possibile inserimento foto

    public ServiceResponse() {
    }


    public ServiceResponse(int idService, String address, String details, int requestUser, String category, int performerUser, Boolean performed, Boolean assistance, Date startSlot, Date endSlot, Date expirationDate, Date insertionDate, Date acceptanceDate) {
        this.idService = idService;
        this.address = address;
        this.details = details;
        this.requestUser = requestUser;
        this.category = category;
        this.performerUser = performerUser;
        this.performed = performed;
        this.assistance = assistance;
        this.startSlot = startSlot;
        this.endSlot = endSlot;
        this.expirationDate = expirationDate;
        this.insertionDate = insertionDate;
        this.acceptanceDate = acceptanceDate;
    }

    public ServiceResponse(int idService, String address, String details, int idUser, String name, int performer, Boolean performed, Boolean assistance) {
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

    public int getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(int requestUser) {
        this.requestUser = requestUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPerformerUser() {
        return performerUser;
    }

    public void setPerformerUser(int performerUser) {
        this.performerUser = performerUser;
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

    public Date getStartSlot() {
        return startSlot;
    }

    public void setStartSlot(Date startSlot) {
        this.startSlot = startSlot;
    }

    public Date getEndSlot() {
        return endSlot;
    }

    public void setEndSlot(Date endSlot) {
        this.endSlot = endSlot;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Date insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Date getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Date acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }
}
