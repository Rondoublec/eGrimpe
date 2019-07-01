package fr.rbo.controller;

import java.util.Collection;

import fr.rbo.model.Spot;
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

import fr.rbo.service.SpotServiceInterface;

import javax.validation.Valid;


@Controller
public class SpotController {

    private static final Logger log = LoggerFactory.getLogger(SpotController.class);

    @Autowired
    SpotServiceInterface spotServiceInterface;


    @GetMapping("/spot")
    public String savePage(Model model) {
        Spot spotForm = new Spot();
        model.addAttribute("spot", spotForm);
//        model.addAttribute("spot", new Spot());

        Collection<Spot> mySpotsList = spotServiceInterface.getAllSpots();
        model.addAttribute("allSpots", mySpotsList);
//        model.addAttribute("allSpots", (ArrayList<Spot>)spotServiceInterface.getAllSpots());
        return "spot";
    }

    @PostMapping("/spot/save")
    public String saveSpot(@ModelAttribute("spot") @Valid Spot spot, BindingResult bindingResult,
                           final RedirectAttributes redirectAttributes) {

        // Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
        if (bindingResult.hasErrors()) {
            return "spot"; // Formulaire en cours sur lequel on veut rester
        }

        if(spotServiceInterface.saveSpot(spot)!=null) {
            redirectAttributes.addFlashAttribute("saveSpot", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveSpot", "unsuccess");
        }

        return "redirect:/spot";
    }

    @RequestMapping(value = "/spot/{operation}/{spotId}", method = RequestMethod.GET)
    public String editRemoveSpot(@PathVariable("operation") String operation,
                                 @PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                                 Model model) {
        if(operation.equals("delete")) {
            if(spotServiceInterface.deleteSpot(spotId)) {
                redirectAttributes.addFlashAttribute("deletion", "success");
            } else {
                redirectAttributes.addFlashAttribute("deletion", "unsuccess");
            }
        } else if(operation.equals("edit")){
            Spot editSpot = spotServiceInterface.findSpot(spotId);
            if(editSpot!=null) {
                model.addAttribute("editSpot", editSpot);
                return "edit-spot-page";
            } else {
                redirectAttributes.addFlashAttribute("status","notfound");
            }
        }

        return "redirect:/spot";
    }

    @RequestMapping(value = "/spot/update", method = RequestMethod.POST)
    public String updateSpot(@ModelAttribute("editSpot") @Valid Spot editSpot, BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes) {

        // Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
        if (bindingResult.hasErrors()) {
            return "edit-spot-page"; // Formulaire sur lequel on veut rester
        }

        if(spotServiceInterface.editSpot(editSpot)!=null) {
            redirectAttributes.addFlashAttribute("edit", "success");
        } else {
            redirectAttributes.addFlashAttribute("edit", "unsuccess");
        }
        return "redirect:/spot";
    }
}
