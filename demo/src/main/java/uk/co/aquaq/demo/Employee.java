package uk.co.aquaq.demo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

@Data
@Entity
public class Employee {

    // Domain object for REST application
    // Entity - JPA annotation, for storage in relational table
    // Id and generated value - JPA annotations for primary key and that it is generated when needed
    // Data for auto-generating setters, getters and constructors

    private @Id @GeneratedValue Long id;
    private String firstName;
    private String lastName;
    private String description;

    private @Version @JsonIgnore Long version;

    private Employee() {

    }

    public Employee(String firstName, String lastName, String description) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
    }
}
