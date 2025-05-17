package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {
@Autowired
private PetStoreDao petStoreDao;
@Autowired
private CustomerDao customerDao;
@Autowired
private EmployeeDao employeeDao;

private void copyPetStoreFields(PetStore petStore,
	PetStoreData petStoreData) {
  petStore.setPetStoreAddress((petStoreData.getPetStoreAddress()));
  petStore.setPetStoreCity(petStoreData.getPetStoreCity());
  petStore.setPetStoreId(petStoreData.getPetStoreId());
  petStore.setPetStoreName(petStoreData.getPetStoreName());
  petStore.setPetStorePhone(petStoreData.getPetStorePhone());
  petStore.setPetStoreState(petStoreData.getPetStoreState());
  petStore.setPetStoreZip(petStoreData.getPetStoreZip());
	
}
private void copyEployeeFields(Employee employee,
     PetStoreEmployee petStoreEmployee) {
  employee.setEmployeeId(petStoreEmployee.getEmployeeId());
  employee.setEmployeeFirstName(petStoreEmployee.getEmployeeFirstName());
  employee.setEmployeeLastName(petStoreEmployee.getEmployeeLastName());
  employee.setEmployeeJobTitle(petStoreEmployee.getEmployeeJobTitle());
  employee.setEmployeePhone(petStoreEmployee.getEmployeePhone());
}

private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
	customer.setCustomerId(petStoreCustomer.getCustomerId());
	customer.setCustomerFirstName(petStoreCustomer.getCustomerFirstName());
	customer.setCustomerLastName(petStoreCustomer.getCustomerLastName());
	customer.setCustomerEmail(petStoreCustomer.getCustomerEmail());
}

private Employee findOrCreateEmployee(Long petStoreId, Long employeeId) {
	if(Objects.isNull(employeeId)) {
	return new Employee();
			
	}
	return findEmployeeById(petStoreId, employeeId);
}
private Customer findOrCreateCustomer(Long petStoreId, Long customerId) {
	if(Objects.isNull(customerId)) {
		return new Customer();
	}
	return findCustomerById(petStoreId, customerId);
}


private Employee findEmployeeById(Long petStoreId, Long employeeId) {
	Employee employee = employeeDao.findById(employeeId).orElseThrow(()
		-> new NoSuchElementException(
		"Employee with Id=" + employeeId + " was not found."));

   if(employee.getPetStore().getPetStoreId() != petStoreId) {
	  throw new IllegalArgumentException("The employee wity ID=" + employeeId
		+ "is notemployed by the petStore with Id=" + petStoreId + ".");
   }	
   	return employee; 
}

private Customer findCustomerById(Long petStoreId, Long customerId ) {
 Customer customer = customerDao.findById(customerId).orElseThrow(() 
	 -> new NoSuchElementException(
		"Customer with Id=" + customerId + " was not found."));
 
 boolean found = false;
 	
 	for(PetStore petStore : customer.getPetStores()) {
 		if(petStore.getPetStoreId() ==petStoreId) {
 			found = true;
 			break;
 		}
 	}
 	if(! found)  {
 		throw new IllegalArgumentException("The customer with ID=" 
 			+customerId + " is not a memeber of the pet store with id=" + petStoreId);
 	}
 	return customer;
}

private PetStore findOrCreatePetStore(Long petStoreId) {
	if(Objects.isNull(petStoreId) ) {
		return new PetStore();
	}
	else {
		return findPetStoreByID(petStoreId);
	}
}

private PetStore findPetStoreByID(Long petStoreId) {
	return petStoreDao.findById(petStoreId)
		.orElseThrow(() -> new NoSuchElementException(
		"Pet Store with ID=" + petStoreId + "was not found"));
		
}
@Transactional
public PetStoreEmployee saveEmployee(Long petStoreId, PetStoreEmployee petStoreEmployee) {
	PetStore petStore = findPetStoreByID(petStoreId);
	Long employeeId = petStoreEmployee.getEmployeeId();
	Employee employee =findOrCreateEmployee(petStoreId, employeeId);
	
	copyEployeeFields(employee, petStoreEmployee);
	
	employee.setPetStore(petStore);
	petStore.getEmployees().add(employee);
	
	Employee dbEmployee = employeeDao.save(employee);
	
	return new PetStoreEmployee(dbEmployee);
	
}
@Transactional
public PetStoreCustomer saveCustomer(Long petStoreId, PetStoreCustomer petStoreCustomer) {
	PetStore petStore = findPetStoreByID(petStoreId);
	Long customerId = petStoreCustomer.getCustomerId();
	Customer customer = findOrCreateCustomer(petStoreId, customerId);
	
	copyCustomerFields(customer, petStoreCustomer);
	
	customer.getPetStores().add(petStore);
	petStore.getCustomers().add(customer);
	
	Customer dbCustomer = customerDao.save(customer);
	
	return new PetStoreCustomer(dbCustomer);

}

@Transactional(readOnly = false)
public PetStoreData savePetstore(PetStoreData petStoreData) {
  Long petStoreId = petStoreData.getPetStoreId();
  PetStore petStore = findOrCreatePetStore(petStoreId);
  
  copyPetStoreFields(petStore, petStoreData);
  return new PetStoreData(petStoreDao.save(petStore));
}

@Transactional(readOnly= true)
public List<PetStoreData> retrieveAllPetStores() {
	 List<PetStore> petStores = petStoreDao.findAll();	
	  
	  List<PetStoreData> result = new LinkedList<>();
		
	  for(PetStore petStore : petStores) {
		PetStoreData psd = new PetStoreData(petStore);
		psd.getCustomers().clear();
	    psd.getEmployees().clear();
	    
	    result.add(psd);
}
	  return result;
}
@Transactional(readOnly = true)
public PetStoreData retrievePetStoreById(Long petStoreId) {
return new PetStoreData (findPetStoreByID(petStoreId));
}
@Transactional(readOnly = false)
public void deletePetStoreById(Long petStoreId) {
PetStore petStore = findPetStoreByID(petStoreId);
petStoreDao.delete(petStore);
	

}

}