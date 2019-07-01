package fr.rbo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import fr.rbo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import fr.rbo.model.User;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value="/login", method = RequestMethod.GET)
	public ModelAndView login(Model model){
		log.info("/login GET");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		log.info("modelAndView", modelAndView.getViewName());
		return modelAndView;
	}
	
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		log.info("/registration GET");
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		log.info("user : ", user.getName(), " ", user.getLastName());
		log.info("modelAndView", modelAndView.getViewName());
		return modelAndView;
	}

	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		log.info("/registration GET : createNewUser");
		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findUserByEmail(user.getEmail());
		if (userExists != null) {
			bindingResult
					.rejectValue("email", "error.user",
							"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("registration");
		}
		log.info("modelAndView", modelAndView.getViewName());
		return modelAndView;
	}
	
	@RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request){
		String url =  request.getRequestURI();
		log.info(url + " GET");
		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = new User();
		if (auth.getPrincipal() == "anonymousUser")
		{
			user.setName("");
			user.setLastName("");
			user.setEmail("Invité");
		}
		else
		{
			user = userService.findUserByEmail(auth.getName());
		}
		modelAndView.addObject("userName", "Bienvenue " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");

		//le test ci-dessous permet de tester des pages d'accueils
		if (url.equals("/home")) {
			modelAndView.setViewName("index");
		}
		else {
			modelAndView.setViewName("index");
		}

		log.info("modelAndView", modelAndView.getViewName());
		return modelAndView;
	}
	

}
