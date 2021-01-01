package org.template.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.sql.Date;

@Entity
@DiscriminatorValue(value = "A")
public class AdminUser extends User{
    public AdminUser() {
    }

    public AdminUser(int idUser, String name, String surname, String email, String password, String address, Date dateOfBirth, String role, String telephone) {
        super(idUser, name, surname, email, password, address, dateOfBirth, role, telephone);
    }


}
