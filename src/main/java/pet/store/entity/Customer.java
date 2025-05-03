package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
//Annotations previously explained in other corresponding classes
@Entity
@Data

// The class Customer is defined as a public class, meaning it can be accessed from other classes in the application.
public class Customer {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long customerId; // Refers to the primary key or unique identifier

// These are String type fields that store the first name, last name, and email of the customer, 
private String customerFirstName;
private String customerLastName;
private String customerEmail;
	
@EqualsAndHashCode.Exclude
@ToString.Exclude

// Many to Many indicates a relationship between two entities. MappedBY attribute specifies the field in the other entity 
// owns the relationship. It is bidirectional and the customers field in the other entity is the one that manages the 
// relationship. This means that the current entity does not have direct control over the join table; instead, the other entity does.
// The cascade attribute defines the cascade operations that should be applied to the relationship. CascadeType.PERSIST means that when 
//the current entity is persisted (saved to the database), the related entities should also be persisted automatically. 
@ManyToMany(mappedBy= "customers", cascade = CascadeType.PERSIST)

// A Set of PetStore objects, initialized as a HashSet. This represents the association between customers and pet stores, 
// indicating that a customer can be associated with multiple pet stores.
private Set<PetStore> petStores = new HashSet<>();



}
