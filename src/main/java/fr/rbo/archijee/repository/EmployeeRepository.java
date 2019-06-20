package fr.rbo.archijee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.rbo.archijee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String>{}
