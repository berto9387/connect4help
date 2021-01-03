package org.template.rest.controller;

import org.template.interfaces.IUserController;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;
import org.template.rest.model.ServiceResponse;
import org.template.rest.util.DecodeToken;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Stateless(name = "UserServiceControllerRestEJB")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserServiceControllerRestBean {
    @Inject
    DecodeToken dT;

    @EJB(name = "UserControllerEJB")
    IUserController userController;

    @Context
    private UriInfo uriInfo;

    private static final String RECORDER_JNDI = "java:comp/env/UserControllerEJB";

    public UserServiceControllerRestBean() {
    }

    ///////////////////////////////////////////
    // endpoint dei servizi relativi all'utente
    /////////////////////////////////////////////

    //ricevi tutti i servizi relativi ad un utente
    @GET
    @Path("/services")
    public Response findUserServices(@Context HttpServletRequest requestContext,
                                     @PathParam("id") Integer id) {
        String authorizationHeader = requestContext.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            dT.decodeToken(authorizationHeader);
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(id!=dT.getId()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(dT.getRole().contains("R")){
            RequestUser user = userController.getRequestUser(id);
            List<ServiceResponse> serviceResponses=new ArrayList<>();
            if (user == null)
                return Response.status(NOT_FOUND).build();
            for (Service s : user.getRequestedServices()){
                ServiceResponse sr = new ServiceResponse(s.getIdService(),s.getAddress(),s.getDetails(),
                        s.getCategory().getName(), s.getPerformed(),s.getAssistance());
                serviceResponses.add(sr);
            }
            return Response.ok(serviceResponses).build();
        } else if(dT.getRole().contains("P")){
            PerformUser user = userController.getPerformUser(id);
            List<ServiceResponse> serviceResponses=new ArrayList<>();
            if (user == null)
                return Response.status(NOT_FOUND).build();
            for (Service s : user.getAcceptedServices()){
                ServiceResponse sr = new ServiceResponse(s.getIdService(),s.getAddress(),s.getDetails(),
                        s.getCategory().getName(), s.getPerformed(),s.getAssistance());
                serviceResponses.add(sr);
            }
            return Response.ok(serviceResponses).build();
        }

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //crea un servizio
    @POST
    @Path("/services")
    public Response createUserService(){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //ricevi uno specifico servizio creato dall'utente id
    @GET
    @Path("/services/{idService}")
    public Response findUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //Cancella uno specifico servizio creato da un utente
    @DELETE
    @Path("/services/{idService}")
    public Response deleteUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //Modifica uno specifico servizio creato da uno specifico utente
    @PUT
    @Path("/services/{idService}")
    public Response modifyUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    ////////////////////////////
    ////PRIVATE METHOD
    ///////////////////////////
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
