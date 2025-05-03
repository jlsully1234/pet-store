package pet.store.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
// The code provided is a Java class definition for an entity named Employee. It is part of a Java 
// application that uses the Spring Framework and Hibernate for object-relational mapping (ORM). 

// The annotations we have already gone over in the PetStore class. to manage database interactions 
//and object-relational mapping, leveraging Lombok to reduce boilerplate code.
@Entity
@Data
public class Employee {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long employeeId; //Primary key for the Employee entity

// These are fields that store the first name, last name, phone number, and job title of the employee. 
private String employeeFirstName;
private String employeeLastName;
private String employeePhone;
private String employeeJobTitle;


@EqualsAndHashCode.Exclude
@ToString.Exclude

// This annotation is used to specify a many-to-one relationship between two entities
// The cascade attribute specifies the cascade operations that should be applied to the 
// related entity. CascadeType.ALL means that all persistence operations (such as persist,
// merge, remove, refresh, detach) will be cascaded to the associated entity. 
@ManyToOne(cascade = CascadeType.ALL)

// This annotation is used to specify the column in the database table that will be used to 
// join the two entities. The name attribute defines the name of the column in the database 
// table that acts as a foreign key, linking the current entity to the related entity. 
// the column is named "pet_store_id."
@JoinColumn(name = "pet_store_id")

// This field represents the association between the Employee and PetStore entities.
private PetStore petStore;




}
