package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.TodoDTO;
import entities.Todo;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//Uncomment the line below, to temporarily disable this test
//@Disabled

public class TodoResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private Todo t1 = new Todo("Title 1", "Description 1");
    private Todo t2 = new Todo("Title 2", "Description 2");
    private Todo t3 = new Todo("Title 3", "Description 3");

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //System.in.read();

        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
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
    public void testServerIsUp() {
        given().when().get("/todo").then().statusCode(403);
    }


    //TODO figure out how to test with login
    /*@Test
    public void testGetTodos() throws Exception {
        given()
                .contentType("application/json")
                .get("/todo").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("size()", equalTo(3));
    }

    @Test
    public void testCreateTodo() throws Exception {
        String title = "Test Title";
        String description = "Test Description";

        TodoDTO todoDTO = new TodoDTO(title,description);

        given()
                .contentType("application/json")
                .body(GSON.toJson(todoDTO))
                .post("/todo").then()
                .assertThat()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .body("title", equalTo(title)).and()
                .body("description", equalTo(description));
    }*/
}
