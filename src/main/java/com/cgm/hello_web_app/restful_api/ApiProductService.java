package com.cgm.hello_web_app.restful_api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cgm.hello_web_app.model.Product;
import com.cgm.hello_web_app.model.ProductDAO;

@Path("/products")
public class ApiProductService {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllProducts_JSON(){
		List<Product> list = null;
		ProductDAO productDAO = new ProductDAO();
		list = productDAO.getAllProducts();
		return list;
	}
	
	@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        boolean success = productDAO.addProduct(product);
        if (success) {
            return Response.status(Response.Status.CREATED).entity("Product added successfully.").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add product.").build();
        }
    }
	
	@DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") Integer id) {
        ProductDAO productDAO = new ProductDAO();
        boolean success = productDAO.deleteProduct(id);
        if (success) {
            return Response.status(Response.Status.OK).entity("Product deleted successfully.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found or failed to delete.").build();
        }
    }
	
	@PUT
    @Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product product) {
        ProductDAO productDAO = new ProductDAO();
        boolean success = productDAO.updateProduct(product);
        if (success) {
            return Response.status(Response.Status.OK).entity("Product update successfully.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Product not found or failed to update.").build();
        }
	}
}
