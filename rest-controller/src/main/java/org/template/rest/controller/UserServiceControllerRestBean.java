package org.template.rest.controller;

import org.template.interfaces.IServiceController;
import org.template.interfaces.IUserController;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;
import org.template.rest.filter.JWTTokenNeeded;
import org.template.rest.filter.RequesterEndPoint;
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

import static java.util.stream.Collectors.toCollection;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Stateless(name = "UserServiceControllerRestEJB")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserServiceControllerRestBean {
    @Inject
    DecodeToken dT;


    @Context
    private UriInfo uriInfo;

    private static final String RECORDER_JNDI = "java:global/app-ear-1.0-SNAPSHOT/controller-1.0-SNAPSHOT/ServiceEJB";

    public UserServiceControllerRestBean() {
    }

    ///////////////////////////////////////////
    // endpoint dei servizi relativi all'utente
    /////////////////////////////////////////////
    @GET
    @JWTTokenNeeded
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

        HttpSession session = requestContext.getSession();
        IServiceController serviceController = lookupServices(session);
        List<ServiceResponse> serviceResponses =new ArrayList<>();
        List<Service> services=serviceController.getUserService(id,dT.getRole())
                    .stream().collect(toCollection(ArrayList::new));

        for (Service s : services){
            ServiceResponse sr = new ServiceResponse(s.getIdService(),s.getAddress(),s.getDetails(),
                    s.getRequestUser().getIdUser(),s.getCategory().getName(),s.getPerformerUser().getIdUser(),
                    s.getPerformed(),s.getAssistance(),s.getStartSlot(),s.getEndSlot(),s.getExpirationDate(),
                    s.getInsertionDate(),s.getAcceptanceDate());
            serviceResponses.add(sr);
        }
        return Response.ok(serviceResponses).build();

    }
    //crea un servizio
    @POST
    @RequesterEndPoint
    @Path("/services")
    public Response createUserService(@Context HttpServletRequest requestContext,
                                      ServiceResponse s,
                                      @PathParam("id") Integer i){

        HttpSession session = requestContext.getSession();
        IServiceController serviceController = lookupServices(session);

        serviceController.createService(s.getAddress(),s.getDetails(),i,s.getCategory(),s.getStartSlot(),s.getEndSlot(),
                s.getExpirationDate());

        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //ricevi uno specifico servizio creato dall'utente id
    @GET
    @JWTTokenNeeded
    @Path("/services/{idService}")
    public Response findUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //Cancella uno specifico servizio creato da un utente
    @DELETE
    @RequesterEndPoint
    @Path("/services/{idService}")
    public Response deleteUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //Modifica uno specifico servizio creato da uno specifico utente
    @PUT
    @JWTTokenNeeded
    @Path("/services/{idService}")
    public Response modifyUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


    ////////////////////////////
    ////PRIVATE METHOD
    ///////////////////////////
    // method to get the stateful bean ref from session, or lookup it
    private IServiceController lookupServices(HttpSession session) {

        IServiceController rRef;
        rRef = (IServiceController) session.getAttribute("cachedRecorderRef");
        if (rRef == null) {

            try {
                javax.naming.Context c = new InitialContext();
                rRef = (IServiceController) c.lookup(RECORDER_JNDI);

            } catch (NamingException ne) {
                java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
                throw new RuntimeException(ne);
            }

            session.setAttribute("cachedRecorderRef", rRef);
        }
        return rRef;
    }
}
