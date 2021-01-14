package org.template.rest.controller;

import org.template.interfaces.ICategoryController;
import org.template.interfaces.IServiceController;
import org.template.model.Category;
import org.template.model.Service;
import org.template.rest.filter.PerformerEndPoint;
import org.template.rest.filter.RequesterEndPoint;
import org.template.rest.model.CategoryResponse;
import org.template.rest.model.ServiceResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Stateless(name = "CategoryControllerRestEJB")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryControllerRestBean {

    @EJB(name = "CategoryControllerEJB")
    ICategoryController categoryController;

    public CategoryControllerRestBean(){

    }

    @GET
    @RequesterEndPoint
    @Path("/")
    public Response findCategories() {

        List<CategoryResponse> categoryResponses =new ArrayList<>();
        List<Category> categories= new ArrayList<>(this.categoryController
                .getCategories());

        for (Category c : categories){
            categoryResponses.add(create_category_response(c));
        }
        return Response.ok(categoryResponses).build();

    }

    private CategoryResponse create_category_response(Category c) {

        CategoryResponse cr = new CategoryResponse();
        cr.setCategoryName(c.getName());
        cr.setPhoto(c.getPhoto());

        return cr;
    }
}
