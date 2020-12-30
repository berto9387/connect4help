package org.template.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.template.controller.IUserController;
import org.template.controller.UserControllerBean;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;
import org.template.model.User;
import org.template.rest.model.RequestNewUser;
import org.template.rest.model.ServerResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.logging.Level;

/**
 * @author Victor Mezrin
 */
@Stateless(name = "UserControllerRestEJB")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserControllerRestBean {

    private Logger LOG = LoggerFactory.getLogger(UserControllerRestBean.class);
    private static final String RECORDER_JNDI = "java:comp/env/UserControllerEJB";

    @EJB(name = "UserControllerEJB")
    IUserController userController;

    @POST
    @Path("/persist")
    public ServerResponse persistUser(@Context HttpServletRequest requestContext,
                                      RequestNewUser request) {
        //this.userController.persistUser(request.getFirstName(), request.getLastName(),request.getRole());

        ServerResponse response = new ServerResponse();
        response.setResult(true);
        return response;
    }
    @POST
    @Path("/retrive")
    public ServerResponse retrieveUser(@Context HttpServletRequest requestContext,
                                      RequestNewUser request) {
        HttpSession session = requestContext.getSession();
        IUserController recorderPerClient = lookupRecorder(session);
        String role=recorderPerClient.receiveRole();


        ServerResponse response = new ServerResponse();

        String ts = role;
        response.setCognome(ts);
        return response;
    }

    @POST
    @Path("/login")
    public ServerResponse loginUser(@Context HttpServletRequest requestContext,
                                       RequestNewUser request) {
        HttpSession session = requestContext.getSession();
        IUserController recorderPerClient = lookupRecorder(session);
        User u=recorderPerClient.loginUser(request.getEmail(),request.getPassword());

        ServerResponse response = new ServerResponse();
        response.setCognome(u.getSurname());
        return response;
    }

    // method to get the stateful bean ref from session, or lookup it
    private IUserController lookupRecorder(HttpSession session) {

        IUserController rRef;
        rRef = (IUserController) session.getAttribute("cachedRecorderRef");
        if (rRef == null) {

            try {
                javax.naming.Context c = new InitialContext();
                rRef = (IUserController) c.lookup(RECORDER_JNDI);

            } catch (NamingException ne) {
                java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
                throw new RuntimeException(ne);
            }

            session.setAttribute("cachedRecorderRef", rRef);
        }
        return rRef;
    }
}
