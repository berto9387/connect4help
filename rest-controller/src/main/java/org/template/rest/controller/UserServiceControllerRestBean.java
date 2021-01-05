package org.template.rest.controller;

import org.template.interfaces.IServiceController;
import org.template.model.Service;
import org.template.rest.filter.JWTTokenNeeded;
import org.template.rest.filter.RequesterEndPoint;
import org.template.rest.model.ServerResponse;
import org.template.rest.model.ServiceRequest;
import org.template.rest.model.ServiceResponse;
import org.template.rest.util.DecodeToken;

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
    @Path("/")
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
        List<Service> services=serviceController.getUserServices(id,dT.getRole())
                    .stream().collect(toCollection(ArrayList::new));

        for (Service s : services){
            int performer=0;
            if(s.getPerformerUser()!=null)
                performer=s.getPerformerUser().getIdUser();
            ServiceResponse sr = new ServiceResponse();

            sr.setIdService(s.getIdService());
            sr.setAddress(s.getAddress());
            sr.setDetails(s.getDetails());
            sr.setCategory(s.getCategory().getName());
            sr.setPerformed(s.getPerformed());
            sr.setAssistance(s.getAssistance());
            sr.setStartSlot(s.getStartSlot());
            sr.setEndSlot(s.getEndSlot());
            sr.setExpirationDate(s.getExpirationDate());
            sr.setInsertionDate(s.getInsertionDate());
            sr.setAcceptanceDate(s.getAcceptanceDate());
            if(dT.getRole().equals("R"))
                sr.setPerformerUser(performer);
            sr.setRequestUser(s.getRequestUser().getIdUser());

            serviceResponses.add(sr);
        }
        return Response.ok(serviceResponses).build();

    }
    //crea un servizio
    @POST
    @RequesterEndPoint
    @Path("/")
    public Response createUserService(@Context HttpServletRequest requestContext,
                                      ServiceRequest s,
                                      @PathParam("id") Integer i){

        HttpSession session = requestContext.getSession();
        IServiceController serviceController = lookupServices(session);

        Boolean er=serviceController.createService(s.getAddress(),s.getDetails(),i,s.getCategory(),s.getStartSlot(),
                s.getEndSlot(),s.getExpirationDate());
        ServerResponse sr = new ServerResponse();
        sr.setResult(er);
        return Response.ok(sr).build();


    }
    //ricevi uno specifico servizio creato dall'utente id
    @GET
    @JWTTokenNeeded
    @Path("/{idService}")
    public Response findUserService(@Context HttpServletRequest requestContext,
                                    @PathParam("id") Integer idUser,
                                    @PathParam("idService") Integer idService){
        String authorizationHeader = requestContext.getHeader(HttpHeaders.AUTHORIZATION);
        try {
            dT.decodeToken(authorizationHeader);
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        if(idUser!=dT.getId()){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        HttpSession session = requestContext.getSession();
        IServiceController serviceController = lookupServices(session);
        Service s=serviceController.getUserService(dT.getId(),idService, dT.getRole());

        int performer=0;
        if(s.getPerformerUser()!=null)
            performer=s.getPerformerUser().getIdUser();
        ServiceResponse sr = new ServiceResponse();

        sr.setIdService(s.getIdService());
        sr.setAddress(s.getAddress());
        sr.setDetails(s.getDetails());
        sr.setCategory(s.getCategory().getName());
        sr.setPerformed(s.getPerformed());
        sr.setAssistance(s.getAssistance());
        sr.setStartSlot(s.getStartSlot());
        sr.setEndSlot(s.getEndSlot());
        sr.setExpirationDate(s.getExpirationDate());
        sr.setInsertionDate(s.getInsertionDate());
        sr.setAcceptanceDate(s.getAcceptanceDate());
        if(dT.getRole().equals("R"))
            sr.setPerformerUser(performer);
        sr.setRequestUser(s.getRequestUser().getIdUser());

        return Response.ok(sr).build();



    }
    //Cancella uno specifico servizio creato da un utente
    @DELETE
    @RequesterEndPoint
    @Path("/{idService}")
    public Response deleteUserService(@Context HttpServletRequest requestContext, @PathParam("id") Integer i){
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    //Modifica uno specifico servizio creato da uno specifico utente
    @PUT
    @JWTTokenNeeded
    @Path("/{idService}")
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
