package fr.rbo.archijee.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.rbo.archijee.model.Employee;
import fr.rbo.archijee.repository.EmployeeRepository;


@Service
@Transactional
public class EmployeeServiceImplementation implements EmployeeServiceInterface{

	private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImplementation.class);

	@Autowired
	protected EmployeeRepository employeeRepository;

	public Employee saveEmployee(Employee emp) {
		// TODO Auto-generated method stub
		return employeeRepository.save(emp);
	}

	public Boolean deleteEmployee(String empId) {
		// TODO Auto-generated method stub
		Employee temp = employeeRepository.getOne(empId);
		if(temp!=null){
			 employeeRepository.delete(temp);
			 return true;
		}
		return false;
	}

	public Employee editEmployee(Employee emp) {
		// TODO Auto-generated method stub
		return employeeRepository.save(emp);
	}

	public Collection<Employee> getAllEmployees() {
		// TODO Auto-generated method stub
		Iterable<Employee> itr = employeeRepository.findAll();
		return (Collection<Employee>)itr;
	}

	public Employee findEmployee(String empId) {
		// TODO Auto-generated method stub
		return employeeRepository.getOne(empId);
	}
	

}
