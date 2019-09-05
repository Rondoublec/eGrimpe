package fr.rbo.controller;

import java.util.Collection;
import java.util.Set;

import fr.rbo.model.Role;
import fr.rbo.model.Spot;
import fr.rbo.model.Commentaire;
import fr.rbo.model.User;
import fr.rbo.service.SpotServiceImpl;
import fr.rbo.service.SpotServiceInterface;
import fr.rbo.service.UserServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SpotController {

    private static final Logger log = LoggerFactory.getLogger(SpotController.class);

    @Autowired
    SpotServiceInterface spotServiceInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;

    @GetMapping("/spot")
    public String afficheSpots(Model model) {
        Collection<Spot> mySpotsList = spotServiceInterface.getAllSpots();
        model.addAttribute("allSpots", mySpotsList);
//        model.addAttribute("allSpots", (ArrayList<Spot>)spotServiceInterface.getAllSpots());
        return "spot-list";
    }

    @GetMapping("/spot/add")
    public String addSpot(Model model) {
        Spot addSpot = new Spot();
        model.addAttribute("spot", addSpot);
//        model.addAttribute("mode", "create");
        return "spot-form";
    }

    @RequestMapping(value = "/spot/delete/{spotId}", method = RequestMethod.GET)
    public String RemoveSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                                 Model model) {
        if(spotServiceInterface.deleteSpot(spotId)) {
            redirectAttributes.addFlashAttribute("deletion", "success");
        } else {
            redirectAttributes.addFlashAttribute("deletion", "unsuccess");
        }
        return "redirect:/spot";
    }

    @RequestMapping(value = "/spot/edit/{spotId}", method = RequestMethod.GET)
    public String EditSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                             Model model) {
        Spot editSpot = spotServiceInterface.findSpot(spotId);
        if(editSpot!=null) {
            model.addAttribute("spot", editSpot);
            return "spot-form";
        } else {
            redirectAttributes.addFlashAttribute("status","notfound");
        }
        return "redirect:/spot";
    }

    @RequestMapping(value = "/spot/secteur/{spotId}", method = RequestMethod.GET)
    public String detailSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                           Model model, HttpSession httpSession) {
        Spot monSpot = spotServiceInterface.findSpot(spotId);
        long idSpot = monSpot.getIdSpot();
        if (idSpot > 0){
            log.info("idSpot > 0 = " + idSpot);
        } else {
            log.info("idSpot < 0 = " + idSpot);
        }
        if(monSpot != null) {
            model.addAttribute("spot", monSpot);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String mailUser = auth.getName();

            User user = userServiceInterface.findUserByEmail(mailUser);
            if (user != null) {
                model.addAttribute("user", user);
            } else {
                User utilisateurNouveau = new User();
                model.addAttribute("user", utilisateurNouveau);
            }
            model.addAttribute("membre", estMembre(user));
            Commentaire Commentaire = new Commentaire();
            model.addAttribute("commentaire",Commentaire);

            return "spot-secteur";
        } else {
            redirectAttributes.addFlashAttribute("status","notfound");
        }
        return "redirect:/spot";
    }

    @PostMapping(value = "/spot/addComment")
    public String ajouterCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,
                                           BindingResult bindingResult, @RequestParam("spotId") Long spotId,
                                           @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Ajouter un commentaire");

        if (bindingResult.hasErrors()){
            /** Garder la liste des secteurs de l'utilisateur */
            majModelSecteur(model,spotId,httpSession);
            commentaire.setMessage("");
            model.addAttribute("commentaire",commentaire);
        } else {
            spotServiceInterface.ajoutCommentaire(commentaire,spotId,email);
            majModelSecteur(model,spotId,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
        }
        return "spot-secteur";
    }
    @PostMapping(value = "/spot/updateComment")
    public String modifCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,
                                         BindingResult result, @RequestParam("spotId") Long spotId,
                                         @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Modification du commentaire par " + email);

        if (result.hasErrors()){
            /** Garder la liste des secteurs de l'utilisateur */
            majModelSecteur(model,spotId,httpSession);
            commentaire.setMessage("");
            model.addAttribute("commentaire",commentaire);
        } else {
            spotServiceInterface.modifCommentaire(commentaire,spotId, email);
            majModelSecteur(model,spotId,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
        }
        return "spot-secteur";
    }
    @PostMapping(value = "/spot/deleteComment")
    public String supprCommentaireSubmit(Model model, @RequestParam("idCommentaire") int idCommentaire,
                                         @RequestParam("spotId") Long spotId,
                                         @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Suppression du commentaire par " + email);

        spotServiceInterface.supprCommentaire(idCommentaire,spotId);
        majModelSecteur(model,spotId,httpSession);
        Commentaire com = new Commentaire();
        model.addAttribute("commentaire",com);
        return "spot-secteur";

    }
    @PostMapping("/spot/save")
    public String saveSpot(@ModelAttribute("spot") @Valid Spot spot,
                           BindingResult bindingResult,
                           final RedirectAttributes redirectAttributes) {

        // Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
        if (bindingResult.hasErrors()) {
            return "spot-form"; // Formulaire en cours sur lequel on veut rester
        }

        if(spotServiceInterface.saveSpot(spot)!=null) {
            redirectAttributes.addFlashAttribute("saveSpot", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveSpot", "unsuccess");
        }

        return "redirect:/spot";
    }

    private void majModelSecteur (Model model, Long spotId, HttpSession httpSession) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String nom = auth.getName();
        User user = userServiceInterface.findUserByEmail(nom);

        if (user != null) {
            httpSession.setAttribute("utilisateurSession", user);
            model.addAttribute("membre", estMembre(user));
            model.addAttribute("user", user);
            model.addAttribute("roles", user.getRoles());
        } else {
            User userVide = new User();
            model.addAttribute("user", userVide);
        }

        Spot spot = spotServiceInterface.findSpot(spotId);
        model.addAttribute("spot", spot);
    }

    private boolean estMembre (User user) {
        boolean membre = false;
        if (user != null) {
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getRole().equals("MEMBRE")) {
                    membre = true;
                }
            }
        }
        return membre;
    }

}
