package org.template.rest.model;

public class ServiceResponse {
    private int idService;
    private String address;
    private String details;
    private int requestUser;
    private String category;
    private int performerUser;
    private Boolean performed;
    private Boolean assistance;
    private String startSlot;
    private String endSlot;
    private String expirationDate;
    private String insertionDate;
    private String acceptanceDate;
    //possibile inserimento foto

    public ServiceResponse() {
    }

    public ServiceResponse(int idService, String address, String details, int requestUser, String category,
                          int performerUser, Boolean performed, Boolean assistance, String startSlot,
                          String endSlot, String expirationDate, String insertionDate, String acceptanceDate) {
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

    public String getStartSlot() {
        return startSlot;
    }

    public void setStartSlot(String startSlot) {
        this.startSlot = startSlot;
    }

    public String getEndSlot() {
        return endSlot;
    }

    public void setEndSlot(String endSlot) {
        this.endSlot = endSlot;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(String insertionDate) {
        this.insertionDate = insertionDate;
    }

    public String getAcceptanceDate() {
        return acceptanceDate;
    }

    public void setAcceptanceDate(String acceptanceDate) {
        this.acceptanceDate = acceptanceDate;
    }
}
