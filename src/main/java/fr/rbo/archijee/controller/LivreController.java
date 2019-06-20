package fr.rbo.archijee.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.rbo.archijee.model.Genre;
import fr.rbo.archijee.model.Livre;
import fr.rbo.archijee.service.GenreServiceHelper;
import fr.rbo.archijee.service.LivreServiceInterface;

@Controller
public class LivreController {

	private static final Logger log = LoggerFactory.getLogger(LivreController.class);

	@Autowired
	LivreServiceInterface livreServiceInterface;
	
	@GetMapping("/livre")
	public String savePage(Model model) {
		log.info("/livre GET");
		model.addAttribute("livreForm", new LivreForm());
		model.addAttribute("genre", new Genre());
		model.addAttribute("allGenres", livreServiceInterface.getAllGenres());
		model.addAttribute("allLivres", livreServiceInterface.getAllLivres());
		log.info("livre", model);
		return "livre";
	}
	
	
	@PostMapping("/livre/save")
	public String savelivre(@ModelAttribute("livreForm") LivreForm livreForm,
			final RedirectAttributes redirectAttributes) {
		Livre livre = convertLivreFormToLivre(livreForm);
		log.info("/livre/save POST");
		if(livreServiceInterface.saveLivre(livre)!=null) {
			redirectAttributes.addFlashAttribute("saveLivre", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveLivre", "unsuccess");
		}
		log.info("redirect:/livre", livre);
		return "redirect:/livre";
	}
	
	private Livre convertLivreFormToLivre(LivreForm livreForm) {
		List<Genre> genres = new ArrayList<Genre>();
		Livre livre = new Livre(livreForm.getTitre());
		log.info("convertLivreFormToLivre");

		for(String genreCode: livreForm.getTabGenres()) {
			Genre genre = GenreServiceHelper.findOne(genreCode);
		    genre.setLivre(livre);
			genres.add(genre);
		}
		
		livre.setGenres(genres);
		log.info("livre", livre);
		return livre;
	}


	@RequestMapping(value = "/livre/{operation}/{empId}", method = RequestMethod.GET)
	public String editRemovelivre(@PathVariable("operation") String operation,
			@PathVariable("empId") Long livreId, final RedirectAttributes redirectAttributes,
			Model model) {
		log.info("/livre/{operation}/{empId}",livreId,"???");
		if(operation.equals("delete")) {
			log.info("/livre/{operation}/{empId}",livreId,"delete");
			if(livreServiceInterface.deleteLivre(livreId)) {
				redirectAttributes.addFlashAttribute("deletion", "success");
			} else {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
			}
		}
		log.info("redirect:/livre", model);
		return "redirect:/livre";
	}
	
	@RequestMapping(value = "/livre/update", method = RequestMethod.POST)
	public String updatelivre(@ModelAttribute("editLivre") Livre editLivre,
			final RedirectAttributes redirectAttributes) {
		log.info("/livre/update",editLivre,"???");
		if(livreServiceInterface.editLivre(editLivre)!=null) {
			log.info("/livre/update",editLivre,"!=null => sucess");
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
//		return "redirect:/savepage";
		log.info("redirect:/livre", editLivre);
		return "redirect:/livre";
	}

}
