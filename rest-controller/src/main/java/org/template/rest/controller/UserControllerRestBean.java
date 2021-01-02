package org.template.rest.controller;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.template.controller.IUserController;
import org.template.model.PerformUser;
import org.template.model.RequestUser;
import org.template.model.Service;
import org.template.model.User;
import org.template.rest.filter.AdminEndPoint;
import org.template.rest.filter.JWTTokenNeeded;
import org.template.rest.filter.RequesterEndPoint;
import org.template.rest.model.*;
import org.template.rest.util.DecodeToken;
import org.template.rest.util.KeyGenerator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.*;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Level;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * @author Victor Mezrin
 */
@Stateless(name = "UserControllerRestEJB")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserControllerRestBean {

    @Inject
    DecodeToken dT;

    private Logger LOG = LoggerFactory.getLogger(UserControllerRestBean.class);
    private static final String RECORDER_JNDI = "java:comp/env/UserControllerEJB";

    @EJB(name = "UserControllerEJB")
    IUserController userController;

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keygen;
    ////////////////////////////////////////
    //endpoint generiche per utente
    /////////////////////////////////////////
    @GET
    public Response findAllUsers() {
        List<User> allUsers = this.userController.findAllUser();
        if (allUsers == null)
            return Response.status(NOT_FOUND).build();
        List<UserResponse> users = new ArrayList<>();
        for (User user : allUsers){
            UserResponse usr = new UserResponse();
            usr.setField(user);
            users.add(usr);
        }
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response findUser(@PathParam("id") Integer id) {
        User user = this.userController.getUser(id);
        if (user == null)
            return Response.status(NOT_FOUND).build();
        UserResponse ur = new UserResponse();
        ur.setField(user);
        return Response.ok(ur).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Integer id) {
        //controllo token
        this.userController.deleteUser(id);
        return Response.noContent().build();
    }

    @PUT
    public Response createUser(@Context HttpServletRequest requestContext,
                                      UserResponse request) {
        this.userController.createUser(request.getName(),request.getSurname(),request.getPassword(),
                                        request.getEmail(),request.getAddress(),request.getDateOfBirth(),
                                        request.getRole(),request.getTelephone());

        return Response.created(uriInfo.getAbsolutePathBuilder().path(Integer.toString(
                request.getIdUser()
        )).build()).build();
    }

    @POST
    @Path("/login")
    public Response authenticateUser(@Context HttpServletRequest requestContext,
                                     RequestLogin request) {
        try {
            // Authenticate the user using the credentials provided
            User u=userController.authenticate(request.getEmail(), request.getPassword());
            if (u==null)
                throw new SecurityException("Invalid email/password");
            // Issue a token for the user
            String token = issueToken(request.getEmail(),u.getIdUser(),u.getRole());
            // Return the token on the response
            return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }
    ///////////////////////////////////////////
    // endpoint dei servizi relativi all'utente
    /////////////////////////////////////////////

    @GET
    @Path("/{id}/services")
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



    private String issueToken(String email,Integer id,String role) {
        Key key = keygen.generateKey();
        Map<String,Object> cl =new HashMap<>();
        cl.put("id",id);
        cl.put("role",role);
        return Jwts.builder()
                .setSubject(email)
                .setClaims(cl)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(3600L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

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

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
