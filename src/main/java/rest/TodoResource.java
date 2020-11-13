package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.TodoDTO;
import entities.Todo;
import errorhandling.MalformedRequest;
import utils.EMF_Creator;
import facades.TodoFacade;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("todo")
public class TodoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final TodoFacade FACADE =  TodoFacade.getTodoFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @RolesAllowed({"user", "admin"})
    @Produces({MediaType.APPLICATION_JSON})
    public String getTodos() {
        List<TodoDTO> todoDTOS = FACADE.getTodos();

        return GSON.toJson(todoDTOS);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String createTodo(String json) throws MalformedRequest {
        TodoDTO todoDTO = GSON.fromJson(json, TodoDTO.class);
        TodoDTO createdTodoDTO = FACADE.createTodo(todoDTO);

        return GSON.toJson(createdTodoDTO);
    }
}
