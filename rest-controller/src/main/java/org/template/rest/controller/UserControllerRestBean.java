package org.template.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.template.controller.IUserController;
import org.template.controller.UserControllerBean;
import org.template.model.Service;
import org.template.model.User;
import org.template.rest.model.RequestNewUser;
import org.template.rest.model.ServerResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * @author Victor Mezrin
 */
@Stateless(name = "UserControllerRestEJB")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserControllerRestBean {

    private Logger LOG = LoggerFactory.getLogger(UserControllerRestBean.class);

    @EJB(name = "UserControllerEJB")
    IUserController userController;

    @POST
    @Path("/persist")
    public ServerResponse persistUser(@Context HttpServletRequest requestContext,
                                      RequestNewUser request) {
        this.userController.persistUser(request.getFirstName(), request.getLastName());

        ServerResponse response = new ServerResponse();
        response.setResult(true);
        return response;
    }
    @POST
    @Path("/retrive")
    public ServerResponse retrieveUser(@Context HttpServletRequest requestContext,
                                      RequestNewUser request) {
        User u=this.userController.retriveUser(request.getFirstName());

        ServerResponse response = new ServerResponse();
        u.getAcceptedServices().iterator().next().setPerformed(false);
        this.userController.persistUser(request.getFirstName(), request.getLastName());
        String aux=Boolean.toString(u.getAcceptedServices().iterator().next().getPerformed());
        System.out.println("paperino "+aux);
        this.LOG.info("Userxxx {}  address", aux);
        response.setCognome(aux);
        return response;
    }
}
