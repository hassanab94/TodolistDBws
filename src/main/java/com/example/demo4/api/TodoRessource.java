package com.example.demo4.api;
import com.example.demo4.data.DataAccess;
import com.example.demo4.model.Todo;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.SQLException;

@Path("/execute")
public class TodoRessource {

    // Récupération de la liste des Todos par User
    @GET
    @Path("/todouser/{userId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String Todoutilisateur(@PathParam("userId") int userId) {
        StringBuilder result = new StringBuilder();
        Connection conn = DataAccess.getConnection();
        result.append(DataAccess.todoparuser(userId));
        return result.toString();
    }
    // Récupération de la liste des Todos par Urgence
    @GET
    @Path("/todourg/{Urnom}")
    @Produces(MediaType.APPLICATION_JSON)
    public String Todourgence(@PathParam("Urnom") String Urnom){
        StringBuilder result = new StringBuilder();
        Connection conn = DataAccess.getConnection();
        result.append(DataAccess.todourgence(Urnom));
        return result.toString();
    }

    @DELETE
    @Path("/delurg/{urgId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String Delurgfromtodo(@PathParam("urgId") int urgId){
        StringBuilder result = new StringBuilder();
        Connection conn = DataAccess.getConnection();
        result.append(DataAccess.delurgence(urgId));
        return result.toString();
    }

    @POST
    @Path("/insertodo/{iduser}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String insertoduser(@PathParam("iduser") int userId, Todo todo) throws SQLException {
        StringBuilder result = new StringBuilder();
        Connection conn = DataAccess.getConnection();
        result.append(DataAccess.inserttodouser(userId, todo));
        return result.toString();
    }

    @POST
    @Path("/insertparnom/{nomuser}/{prenomuser}")
    @Produces(MediaType.APPLICATION_JSON)
    public String insertparnom (@PathParam("nomuser") String nomuser,@PathParam("prenomuser") String prenomuser, Todo todo) throws SQLException {
        StringBuilder result = new StringBuilder();
        Connection conn = DataAccess.getConnection();
        result.append(DataAccess.insertodoparnom(nomuser, prenomuser, todo));
        return result.toString();
    }

    @DELETE
    @Path("/deltodo/{userid}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deltodoparuser(@PathParam("userid") int userid, Todo todo){
        StringBuilder result = new StringBuilder();
        Connection conn = DataAccess.getConnection();
        result.append(DataAccess.deluserdeltodo( userid, todo));
        return result.toString();
    }


}



