package org.template.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Entity
public class Service implements Serializable {
    private int idService;
    private String address;
    private String details;
    private User requestUser;
    private Category category;
    private User performerUser;
    private Boolean performed;
    private Boolean assistance;

    @Id
    @Column(name = "idService")
    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    @Basic
    @Column(name = "Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "Performed")
    public Boolean getPerformed() {
        return performed;
    }

    public void setPerformed(Boolean performed) {
        this.performed = performed;
    }

    @Basic
    @Column(name = "Assistance")
    public Boolean getAssistance() {
        return assistance;
    }

    public void setAssistance(Boolean assistance) {
        this.assistance = assistance;
    }

    @Basic
    @Column(name = "Details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return idService == service.idService &&
                Objects.equals(address, service.address) &&
                Objects.equals(details, service.details) &&
                Objects.equals(requestUser, service.requestUser) &&
                Objects.equals(category, service.category) &&
                Objects.equals(performerUser, service.performerUser) &&
                Objects.equals(performed, service.performed) &&
                Objects.equals(assistance, service.assistance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idService, address, details, requestUser, category, performerUser, performed, assistance);
    }

    @ManyToOne
    @JoinColumn(name = "IdRequestUser", referencedColumnName = "idUser", nullable = false)
    public User getRequestUser() {
        return requestUser;
    }

    public void setRequestUser(User requestUser) {
        this.requestUser = requestUser;
    }

    @ManyToOne
    @JoinColumn(name = "Category_Name", referencedColumnName = "Name", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @ManyToOne
    @JoinColumn(name = "IdPerformerUser", referencedColumnName = "idUser")
    public User getPerformerUser() {
        return performerUser;
    }

    public void setPerformerUser(User performerUser) {
        this.performerUser = performerUser;
    }
}
