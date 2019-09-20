package fr.rbo.controller;

import java.util.Collection;
import java.util.Set;

import fr.rbo.model.*;
import fr.rbo.service.SpotServiceInterface;
import fr.rbo.service.UserServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/spot/delete/{spotId}")
    public String RemoveSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                                 Model model) {
        if(spotServiceInterface.deleteSpot(spotId)) {
            redirectAttributes.addFlashAttribute("deletion", "success");
        } else {
            redirectAttributes.addFlashAttribute("deletion", "unsuccess");
        }
        return "redirect:/spot";
    }

    @GetMapping("/spot/edit/{spotId}")
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

    @GetMapping("/secteur/voies")
    public String affichelesVoies(Model model, @RequestParam("idSpot") Long idSpot,
                                  @RequestParam("idSecteur") int idSecteur,
                                  HttpSession httpSession) {
        log.debug("affiche les voies du secteur");
        Spot spot = spotServiceInterface.findSpot(idSpot);
        Secteur secteur = spotServiceInterface.getSecteur(idSecteur);
        majModelSecteur(model,idSpot,httpSession);
//        model.addAttribute("spot", spot);
        model.addAttribute("secteur", secteur);
        return "secteur-voie";
    }

    @GetMapping("/secteur/addVoie")
    public String ajoutVoie(Model model,@RequestParam("idSpot") Long idSpot,@RequestParam("idSecteur") int idSecteur) {
        /* Création d'une voie */
        log.debug("addvoie "  + idSpot + " & Secteur " + idSecteur);
        Voie voie = new Voie();
        model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
        model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
        model.addAttribute("voie", voie);
        return "voie-form";
    }

    @PostMapping(value = "/secteur/addVoie")
    public String proposerVoieSubmit(Model model, @Valid @ModelAttribute("voie") Voie voie,
                                        BindingResult result, @RequestParam("idSpot") Long idSpot,
                                        @RequestParam("idSecteur") int idSecteur,
                                        HttpSession httpSession) {

        log.debug("submit du formulaire voie");
        log.debug("addvoie create voie "  + idSpot + " & Secteur " + idSecteur);

        if (result.hasErrors()){
            log.debug("erreur du validation du formulaire secteur");
            model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
            model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
            return "voie-form";
        } else {
            if(spotServiceInterface.ajoutVoie(idSecteur, voie)!=null) {
                model.addAttribute("result", "success");
                model.addAttribute("resultext", "Voie enregistrée avec succès");
            } else {
                model.addAttribute("result", "unsuccess");
                model.addAttribute("resultext", "Voie non enregistrée, contactez l'informatique");
            }
            majModelSecteur(model,idSpot,httpSession);
//            model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
            model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
            return "secteur-voie";

        }
    }

    @GetMapping("/voie/longueurs")
    public String affichelesLongueurs(Model model, @RequestParam("idSpot") Long idSpot,@RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie) {
        log.debug("affiche les longuers de la voie");
        Spot spot = spotServiceInterface.findSpot(idSpot);
        Secteur secteur = spotServiceInterface.getSecteur(idSecteur);
        Voie voie = spotServiceInterface.getVoie(idVoie);
        model.addAttribute("spot", spot);
        model.addAttribute("secteur", secteur);
        model.addAttribute("voie",voie);
        return "voie-longueur";
    }

    @GetMapping("/spot/secteur/{spotId}")
    public String detailSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                           Model model, HttpSession httpSession) {
        Spot monSpot = spotServiceInterface.findSpot(spotId);
        long idSpot = monSpot.getIdSpot();
        if (idSpot > 0){
            log.debug("idSpot > 0 = " + idSpot);
        } else {
            log.debug("idSpot < 0 = " + idSpot);
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

    @GetMapping("/spot/addSecteur")
    public String ajoutSecteur(Model model,@RequestParam("idSpot") Long idSpot) {

        /* Création d'un secteur */
        log.debug("addsecteur "  + idSpot);
        Secteur secteur = new Secteur();
        model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
        model.addAttribute("secteur", secteur);

        return "secteur-form";
    }

    @PostMapping(value = "/spot/addSecteur")
    public String proposerSecteurSubmit(Model model, @Valid @ModelAttribute("secteur") Secteur secteur,
                                        BindingResult result, @RequestParam("idSpot") Long idSpot,
                                        HttpSession httpSession) {

        log.debug("submit du formulaire secteur");
        log.debug("addsecteur create secteur "  + idSpot);

        if (result.hasErrors()){
            log.debug("erreur du validation du formulaire secteur");
            model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
            return "secteur-form";
        } else {
            if(spotServiceInterface.ajoutSecteur(idSpot, secteur)!=null) {
                model.addAttribute("result", "success");
                model.addAttribute("resultext", "Secteur enregistré avec succès");
            } else {
                model.addAttribute("result", "unsuccess");
                model.addAttribute("resultext", "Secteur non enregistré, contactez l'informatique");
            }
            majModelSecteur(model,idSpot,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
            return "spot-secteur";

        }
    }

    @PostMapping(value = "/spot/deleteSecteur")
    public String supprSecteurSubmit(Model model, @RequestParam("idSecteur") int idSecteur,
                                         @RequestParam("spotId") Long spotId,
                                         @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Suppression du secreur " + idSecteur + " par " + email);
        spotServiceInterface.supprSecteur(idSecteur,spotId);
        majModelSecteur(model,spotId,httpSession);
        Commentaire com = new Commentaire();
        model.addAttribute("commentaire",com);
        return "spot-secteur";

    }

    @PostMapping(value = "/spot/addComment")
    public String ajouterCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,
                                           BindingResult bindingResult, @RequestParam("spotId") Long spotId,
                                           @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Ajouter un commentaire");

        if (bindingResult.hasErrors()){
            log.warn("Commentaire non valide");
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
