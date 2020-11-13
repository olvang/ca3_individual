package entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@NamedQuery(name = "Todo.deleteAllRows", query = "DELETE from Todo")
public class Todo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    public Todo() { }


    public Todo(String title, String description) {
        this.title = title;
        this.description = description;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
