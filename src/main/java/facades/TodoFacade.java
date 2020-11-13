package facades;

import dto.TodoDTO;
import entities.Todo;
import errorhandling.MalformedRequest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class TodoFacade {

    private static TodoFacade instance;
    private static EntityManagerFactory emf;
    
    //Private Constructor to ensure Singleton
    private TodoFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static TodoFacade getTodoFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TodoFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<TodoDTO> getTodos(){
        EntityManager em = emf.createEntityManager();
        try{
            List<TodoDTO> todoDTOS = new ArrayList<>();
            List<Todo> todos = em.createQuery("SELECT t FROM Todo t").getResultList();
            for (Todo todo : todos){
                TodoDTO todoDTO = new TodoDTO(todo);
                todoDTOS.add(todoDTO);
            }

            return todoDTOS;
        }finally{  
            em.close();
        }
        
    }

    public TodoDTO createTodo(TodoDTO todoDTO) throws MalformedRequest {
        EntityManager em = emf.createEntityManager();
        if(isNullOrEmpty(todoDTO.getTitle()) || isNullOrEmpty(todoDTO.getDescription())){
            throw new MalformedRequest("Error todo must contain a title and description");
        }

        Todo todo = new Todo(todoDTO.getTitle(), todoDTO.getDescription());
        try{
                em.getTransaction().begin();
                em.persist(todo);
                em.getTransaction().commit();

            return new TodoDTO(todo);
        }finally{
            em.close();
        }
    }

    private boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}
