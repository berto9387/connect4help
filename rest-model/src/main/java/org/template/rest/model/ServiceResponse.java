package org.template.rest.model;

import javax.json.bind.annotation.JsonbDateFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.template.model.Category;
import org.template.model.PerformUser;
import org.template.model.RequestUser;

import java.sql.Timestamp;
import java.time.LocalDate;

public class ServiceResponse {
    private int idService;
    private String address;
    private String details;
    private int requestUser;
    private String category;
    private int performerUser;
    private Boolean performed;
    private Boolean assistance;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate startSlot;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate endSlot;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate expirationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate insertionDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate acceptanceDate;
    //possibile inserimento foto

    public ServiceResponse() {
    }



    public ServiceResponse(int idService, String address, String details, int requestUser, String category,
                           int performerUser, Boolean performed, Boolean assistance, Timestamp startSlot,
                           Timestamp endSlot, Timestamp expirationDate, Timestamp insertionDate,
                           Timestamp acceptanceDate) {
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

    public Timestamp getStartSlot() {
        return startSlot;
    }

    public void setStartSlot(Timestamp startSlot) {
        this.startSlot = startSlot;
    }

    public Timestamp getEndSlot() {
        return endSlot;
    }

    public void setEndSlot(Timestamp endSlot) {
        this.endSlot = endSlot;
    }

    public Timestamp getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Timestamp expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Timestamp getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Timestamp insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Timestamp getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(Timestamp acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }
}
