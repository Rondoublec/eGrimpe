package fr.rbo.archijee.controller;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.rbo.archijee.model.Employee;
import fr.rbo.archijee.service.EmployeeServiceInterface;

import javax.validation.Valid;


@Controller
public class EmployeeController {

	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeServiceInterface employeeServiceInterface;
	
	
	@GetMapping("/employee")
	public String savePage(Model model) {
		Employee employeeForm = new Employee();
		model.addAttribute("employee", employeeForm);
//		model.addAttribute("employee", new Employee();

		Collection<Employee> myEmployeesList = employeeServiceInterface.getAllEmployees();
		model.addAttribute("allEmployees", myEmployeesList);
//		model.addAttribute("allEmployees", (ArrayList<Employee>)employeeServiceInterface.getAllEmployees());

		return "employee";
	}
	
	@PostMapping("/employee/save")
	public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee , BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			return "employee"; // Formulaire en cours sur lequel on veut rester
		}

		if(employeeServiceInterface.saveEmployee(employee)!=null) {
			redirectAttributes.addFlashAttribute("saveEmployee", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveEmployee", "unsuccess");
		}
		
		return "redirect:/employee";
	}
	
	@RequestMapping(value = "/employee/{operation}/{empId}", method = RequestMethod.GET)
	public String editRemoveEmployee(@PathVariable("operation") String operation,
			@PathVariable("empId") String empId, final RedirectAttributes redirectAttributes,
			Model model) {
		if(operation.equals("delete")) {
			if(employeeServiceInterface.deleteEmployee(empId)) {
				redirectAttributes.addFlashAttribute("deletion", "success");
			} else {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
			}
		} else if(operation.equals("edit")){
		  Employee editEmployee = employeeServiceInterface.findEmployee(empId);
		  if(editEmployee!=null) {
		       model.addAttribute("editEmployee", editEmployee);
		       return "editEmployeePage";
		  } else {
			  redirectAttributes.addFlashAttribute("status","notfound");
		  }
		}
		
		return "redirect:/employee";
	}
	
	@RequestMapping(value = "/employee/update", method = RequestMethod.POST)
	public String updateEmployee(@ModelAttribute("editEmployee") @Valid Employee editEmployee, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {

		// Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
		if (bindingResult.hasErrors()) {
			return "editEmployeePage"; // Formulaire sur lequel on veut rester
		}

		if(employeeServiceInterface.editEmployee(editEmployee)!=null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
//		return "redirect:/savepage";
		return "redirect:/employee";
	}
}
