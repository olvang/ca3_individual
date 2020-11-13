package facades;

import dto.TodoDTO;
import entities.Todo;
import errorhandling.MalformedRequest;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

//Uncomment the line below, to temporarily disable this test
//@Disabled
public class TodoFacadeTest {

    private static EntityManagerFactory emf;
    private static TodoFacade facade;

    private Todo t1 = new Todo("Title 1", "Description 1");
    private Todo t2 = new Todo("Title 2", "Description 2");
    private Todo t3 = new Todo("Title 3", "Description 3");

    public TodoFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
       emf = EMF_Creator.createEntityManagerFactoryForTest();
       facade = TodoFacade.getTodoFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the code below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Todo.deleteAllRows").executeUpdate();

            em.persist(t1);
            em.persist(t2);
            em.persist(t3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetTodos() {
        List<TodoDTO> todoDTOS = facade.getTodos();

        assertEquals(3, todoDTOS.size(), "Expects three rows in the database");
    }

    @Test
    public void testCreateTodo() throws MalformedRequest {
        String title = "Test Title";
        String description = "Test Description";

        TodoDTO todoDTO = new TodoDTO(title,description);

        TodoDTO createdTodoDTO = facade.createTodo(todoDTO);

        assertEquals(title, createdTodoDTO.getTitle(), "Expects the titles to be the same");
    }

    @Test
    public void testCreateTodoEmptyTitle() throws MalformedRequest {
        String title = "";
        String description = "Test Description";

        TodoDTO todoDTO = new TodoDTO(title,description);

        MalformedRequest assertThrows = Assertions.assertThrows(MalformedRequest.class, () -> {
            facade.createTodo(todoDTO);
        });


        assertNotNull(assertThrows);
    }


    @Test
    public void testCreateTodoEmptyDescription() throws MalformedRequest {
        String title = "Test Title";
        String description = "";

        TodoDTO todoDTO = new TodoDTO(title,description);

        MalformedRequest assertThrows = Assertions.assertThrows(MalformedRequest.class, () -> {
            facade.createTodo(todoDTO);
        });


        assertNotNull(assertThrows);
    }


}
