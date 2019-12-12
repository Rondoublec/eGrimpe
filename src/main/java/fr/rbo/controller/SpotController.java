package fr.rbo.controller;

import java.util.List;
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

/**
 * Controleur de la gestion des Spots / secteurs / voies / longueurs
 */
@Controller
public class SpotController {

    private static final Logger log = LoggerFactory.getLogger(SpotController.class);

    @Autowired
    SpotServiceInterface spotServiceInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;

    /**
     * Appel à la page d'admin
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping({"/adm1n", "/adm1n/test"})
    public String Admin(Model model, HttpSession httpSession) {
        log.info("Appel à la Page Adm1n");
        majModel(model,null,httpSession);
        return "adm1n";
    }

    /**
     * Appel à la liste des spots
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/spot")
    public String Spot(Model model, HttpSession httpSession) {
        log.debug("recherche-spot-list : Liste des spots complète");
        Spot spotCriteres = new Spot();
        List<Spot> listSpots= spotServiceInterface.recupSpots(spotCriteres);
        model.addAttribute("spotCriteres", spotCriteres);
        model.addAttribute("listSpots", listSpots);
        majModel(model,null,httpSession);
        return "recherche-spot-list";
    }

    /**
     * Recherche et affiche la liste des sites correspondants aux critèrs de recherche
     * @param model
     * @param spotCriteres
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/spot/recherche")
    public String SpoteRecherche (Model model, @ModelAttribute ("spotCriteres") Spot spotCriteres,
                                          HttpSession httpSession) {
        log.debug("recherche-spot-list : Liste des spot/recherche");
        log.debug("labelAmi " + spotCriteres.isLabelAmi());
        List<Spot> listSpots= spotServiceInterface.recupSpots(spotCriteres);
        model.addAttribute("spotCriteres", spotCriteres);
        model.addAttribute("listSpots", listSpots);
        majModel(model,null,httpSession);
        return "recherche-spot-list";
    }

    /**
     * Appel le formulaire de création d'un spot
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/spot/add")
    public String addSpot(Model model, HttpSession httpSession) {
        log.debug("spot-form : Ajout d'un spot");
        Spot addSpot = new Spot();
        model.addAttribute("spot", addSpot);
        majModel(model,null,httpSession);
        return "spot-form";
    }

    /**
     * Supprime le spot correspondant au spotId
     * @param spotId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/spot/delete/{spotId}")
    public String RemoveSpot(@PathVariable("spotId") Long spotId,
                             final RedirectAttributes redirectAttributes) {
        String email = recupUser().getEmail();
        if (!estMembre(recupUser())) {
            log.info("ALERTE SECURITE tentative de suppression spot : " + spotId + " par " + email + " NON AUTORISEE");
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            return "redirect:/spot";
        }
        log.info("Suppression spot : " + spotId + " par " + email);
        if(spotServiceInterface.deleteSpot(spotId)) {
            redirectAttributes.addFlashAttribute("deletion", "success");
        } else {
            redirectAttributes.addFlashAttribute("deletion", "unsuccess");
        }
        return "redirect:/spot";
    }

    /**
     * Appel le formulaire de modification des informations du spot correspondant au spotId
     * @param spotId
     * @param redirectAttributes
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/spot/edit/{spotId}")
    public String EditSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                             Model model, HttpSession httpSession) {
        log.debug("demande de modification spot : " + spotId);
        Spot editSpot = spotServiceInterface.findSpot(spotId);
        if(editSpot!=null) {
            majModel(model,null,httpSession);
            model.addAttribute("spot", editSpot);
            return "spot-form";
        } else {
            redirectAttributes.addFlashAttribute("status","notfound");
        }
        return "redirect:/spot";
    }

    /**
     * Appel l'affichage des informations des secteurs du spot correspondant au spotId
     * @param spotId
     * @param redirectAttributes
     * @param model
     * @return
     */
    @GetMapping("/spot/secteur/{spotId}")
    public String detailSpot(@PathVariable("spotId") Long spotId, final RedirectAttributes redirectAttributes,
                           Model model) {
        log.debug("spot-secteur : Liste des secteurs du spot : " + spotId);
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

    /**
     * Appel le formulaire de création d'un secteur pour le spot correspondant au spotId
     * @param model
     * @param idSpot
     * @return
     */
    @GetMapping("/spot/addSecteur")
    public String ajoutSecteur(Model model,@RequestParam("idSpot") Long idSpot) {
        /* Création d'un secteur */
        log.debug("secteur-form : Ajout d'un secteur sur le spot " + idSpot);
        Secteur secteur = new Secteur();
        model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
        model.addAttribute("secteur", secteur);

        return "secteur-form";
    }

    /**
     * Soumet les informations du secteur à créer
     * Et appel la création du secteur
     * @param model
     * @param secteur
     * @param result
     * @param idSpot
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/spot/addSecteur")
    public String proposerSecteurSubmit(Model model, @Valid @ModelAttribute("secteur") Secteur secteur,
                                        BindingResult result, @RequestParam("idSpot") Long idSpot,
                                        HttpSession httpSession) {

        log.debug("submit du formulaire secteur");
        log.debug("addsecteur create spot "  + idSpot);
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
            majModel(model,idSpot,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
            return "spot-secteur";

        }
    }

    /**
     * Supprime le secteur correspondant à l'idSecteur envoyé dans le formulaire
     * @param model
     * @param idSecteur
     * @param spotId
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/spot/deleteSecteur")
    public String supprSecteurSubmit(Model model, @RequestParam("idSecteur") int idSecteur,
                                         @RequestParam("spotId") Long spotId,
                                         @RequestParam("email") String email, HttpSession httpSession) {
        log.info("Suppression du secteur " + idSecteur + " du spot " + spotId + " par " + email);
        spotServiceInterface.supprSecteur(idSecteur,spotId);
        majModel(model,spotId,httpSession);
        Commentaire com = new Commentaire();
        model.addAttribute("commentaire",com);
        return "spot-secteur";
    }

    /**
     * Enregistre le commentaire de l'utilisateur connecté
     * pour le spot correspondant à spotId
     * @param model
     * @param commentaire
     * @param bindingResult
     * @param spotId
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/spot/addComment")
    public String ajouterCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,
                                           BindingResult bindingResult, @RequestParam("spotId") Long spotId,
                                           @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Ajouter un commentaire");

        if (bindingResult.hasErrors()){
            log.warn("Commentaire non valide");
            /** Garder la liste des secteurs de l'utilisateur */
            majModel(model,spotId,httpSession);
            commentaire.setMessage("");
            model.addAttribute("commentaire",commentaire);
        } else {
            spotServiceInterface.ajoutCommentaire(commentaire,spotId,email);
            majModel(model,spotId,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
        }
        return "spot-secteur";
    }

    /**
     *      * Met à jour le commentaire par l'utilisateur connecté
     *      * pour le spot correspondant à spotId
     * @param model
     * @param commentaire
     * @param result
     * @param spotId
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/spot/updateComment")
    public String modifCommentaireSubmit(Model model, @Valid @ModelAttribute("commentaire") Commentaire commentaire,
                                         BindingResult result, @RequestParam("spotId") Long spotId,
                                         @RequestParam("email") String email, HttpSession httpSession) {

        log.info("Modification du commentaire par " + email);

        if (result.hasErrors()){
            /** Garder la liste des secteurs de l'utilisateur */
            majModel(model,spotId,httpSession);
            model.addAttribute("commentaire",commentaire);
        } else {
            spotServiceInterface.modifCommentaire(commentaire,spotId, email);
            majModel(model,spotId,httpSession);
            Commentaire com = new Commentaire();
            model.addAttribute("commentaire",com);
        }
        return "spot-secteur";
    }

    /**
     * Supprime le commentaire correspondant à idCommentaire
     * pour le spot correspondant à spotId
     * @param model
     * @param idCommentaire
     * @param spotId
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/spot/deleteComment")
    public String supprCommentaireSubmit(Model model, @RequestParam("idCommentaire") int idCommentaire,
                                         @RequestParam("spotId") Long spotId,
                                         @RequestParam("email") String email, HttpSession httpSession) {
        log.info("Suppression du commentaire " + idCommentaire + " du spot " + spotId + " par " + email);
        spotServiceInterface.supprCommentaire(idCommentaire,spotId);
        majModel(model,spotId,httpSession);
        Commentaire com = new Commentaire();
        model.addAttribute("commentaire",com);
        return "spot-secteur";

    }

    /**
     * Enregistre les informations du spot
     * En création et en modification
     * @param model
     * @param spot
     * @param bindingResult
     * @param httpSession
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/spot/save")
    public String saveSpot(Model model, @ModelAttribute("spot") @Valid Spot spot,
                           BindingResult bindingResult, HttpSession httpSession,
                           final RedirectAttributes redirectAttributes) {

        // Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
        if (bindingResult.hasErrors()) {
            majModel(model,null,httpSession);
            return "spot-form"; // Formulaire en cours sur lequel on veut rester
        }

        if(spotServiceInterface.saveSpot(spot)!=null) {
            redirectAttributes.addFlashAttribute("saveSpot", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveSpot", "unsuccess");
        }

        return "redirect:/spot";
    }

    /**
     * Appel l'affichage des voies du secteur correspondant à idSecteur
     * Pour le spot correspondant à idSpot
     * @param model
     * @param idSpot
     * @param idSecteur
     * @param httpSession
     * @return
     */
    @GetMapping("/secteur/voies")
    public String affichelesVoies(Model model, @RequestParam("idSpot") Long idSpot,
                                  @RequestParam("idSecteur") int idSecteur,
                                  HttpSession httpSession) {
        log.debug("affiche les voies du secteur");
        Spot spot = spotServiceInterface.findSpot(idSpot);
        Secteur secteur = spotServiceInterface.getSecteur(idSecteur);
        majModel(model,idSpot,httpSession);
        model.addAttribute("secteur", secteur);
        return "secteur-voie";
    }

    /**
     * Appel le formulaire de création de voie pour le secteur correspondant à idSecteur
     * Pour le spot correspondant à idSpot
     * @param model
     * @param idSpot
     * @param idSecteur
     * @return
     */
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

    /**
     * Soumet les informations de création de la voie pour le secteur correspondant à idSecteur
     * Pour le spot correspondant à idSpot
     * Et appel la sauvegarde de la voie
     * @param model
     * @param voie
     * @param result
     * @param idSpot
     * @param idSecteur
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/secteur/addVoie")
    public String proposerVoieSubmit(Model model, @Valid @ModelAttribute("voie") Voie voie,
                                     BindingResult result, @RequestParam("idSpot") Long idSpot,
                                     @RequestParam("idSecteur") int idSecteur,
                                     HttpSession httpSession) {

        log.debug("submit du formulaire voie");
        log.debug("addvoie create spot "  + idSpot + " & Secteur " + idSecteur);

        if (result.hasErrors()){
            log.debug("erreur du validation du formulaire voie");
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
            majModel(model,idSpot,httpSession);
//            model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
            model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
            return "secteur-voie";

        }
    }

    /**
     * Supprime la voie correspondante à idVoie du secteur correspondant à idSecteur
     * pour le spot correspondant à idSpot
     * @param model
     * @param idVoie
     * @param idSecteur
     * @param spotId
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/secteur/deleteVoie")
    public String supprVoieSubmit(Model model, @RequestParam("idVoie") int idVoie,
                                  @RequestParam("idSecteur") int idSecteur,
                                  @RequestParam("spotId") Long spotId,
                                  @RequestParam("email") String email, HttpSession httpSession) {
        log.info("Suppression de la voie " + idVoie + "du secteur " + idSecteur + " du spot " + spotId + " par " + email);
        spotServiceInterface.supprVoie(idVoie,idSecteur);
        majModel(model,spotId,httpSession);
        Secteur secteur = spotServiceInterface.getSecteur(idSecteur);
        model.addAttribute("secteur", secteur);
        return "secteur-voie";
    }

    /**
     * Appel l'affichage des informations des longueurs de la voie correspondante à idVoie
     * du secteur correspondant à idSecteur pour le site correspondant à idSpot
     * @param model
     * @param idSpot
     * @param idSecteur
     * @param idVoie
     * @param httpSession
     * @return
     */
    @GetMapping("/voie/longueurs")
    public String affichelesLongueurs(Model model, @RequestParam("idSpot") Long idSpot,
                                      @RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie,
                                      HttpSession httpSession) {
        log.debug("affiche les longueurs de la voie");
        Spot spot = spotServiceInterface.findSpot(idSpot);
        Secteur secteur = spotServiceInterface.getSecteur(idSecteur);
        Voie voie = spotServiceInterface.getVoie(idVoie);
        majModel(model,idSpot,httpSession);
        model.addAttribute("secteur", secteur);
        model.addAttribute("voie",voie);
        return "voie-longueur";
    }

    /**
     * Appel le formulaire de saisie des informations d'une longueurs de la voie correspondante à idVoie
     * du secteur correspondant à idSecteur pour le site correspondant à idSpot
     * @param model
     * @param idSpot
     * @param idSecteur
     * @param idVoie
     * @return
     */
    @GetMapping("/voie/addLongueur")
    public String ajoutLongueur(Model model,@RequestParam("idSpot") Long idSpot,
                                @RequestParam("idSecteur") int idSecteur,@RequestParam("idVoie") int idVoie) {
        /* Création d'une longueur */
        log.debug("addlongueur "  + idSpot + " & Secteur " + idSecteur + " & Voie " + idVoie);
        Longueur longueur = new Longueur();
        model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
        model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
        model.addAttribute("voie", spotServiceInterface.getVoie(idVoie));
        model.addAttribute("longueur", longueur);
        return "longueur-form";
    }

    /**
     * Soumet les informations de création de la longueur de la voie correspondante à idVoie
     * pour le secteur correspondant à idSecteur du spot correspondant à idSpot
     * Et appel la sauvegarde de la longueur
     * @param model
     * @param longueur
     * @param result
     * @param idSpot
     * @param idSecteur
     * @param idVoie
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/voie/addLongueur")
    public String proposerLongueurSubmit(Model model, @Valid @ModelAttribute("longueur") Longueur longueur,
                                         BindingResult result, @RequestParam("idSpot") Long idSpot,
                                         @RequestParam("idSecteur") int idSecteur,
                                         @RequestParam("idVoie") int idVoie,
                                         HttpSession httpSession) {

        log.info("submit du formulaire longueur");
        log.debug("addlongueur create spot "  + idSpot + " & Secteur " + idSecteur  + " & Voie " + idVoie);

        if (result.hasErrors()){
            log.debug("erreur du validation du formulaire longueur");
            model.addAttribute("spot", spotServiceInterface.findSpot(idSpot));
            model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
            model.addAttribute("voie", spotServiceInterface.getVoie(idVoie));
            return "longueur-form";
        } else {
            if(spotServiceInterface.ajoutLongueur(idVoie, longueur)!=null) {
                model.addAttribute("result", "success");
                model.addAttribute("resultext", "Longueur enregistrée avec succès");
            } else {
                model.addAttribute("result", "unsuccess");
                model.addAttribute("resultext", "Longueur non enregistrée, contactez l'informatique");
            }
            majModel(model,idSpot,httpSession);
            model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
            model.addAttribute("voie", spotServiceInterface.getVoie(idVoie));
            return "voie-longueur";

        }
    }

    /**
     * Supprime la longueur correspondante à idLongueur de la voie correspondante à idVoie
     * pour le secteur correspondant à idSecteur du spot correspondant à idSpot
     * @param model
     * @param idLongueur
     * @param idVoie
     * @param idSecteur
     * @param spotId
     * @param email
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/voie/deleteLongueur")
    public String supprLongueurSubmit(Model model, @RequestParam("idLongueur") int idLongueur,
                                      @RequestParam("idVoie") int idVoie,
                                      @RequestParam("idSecteur") int idSecteur,
                                      @RequestParam("spotId") Long spotId,
                                      @RequestParam("email") String email, HttpSession httpSession) {
        log.info("Suppression de la longueur " + idLongueur + " de la voie " + idVoie + " du secteur " + idSecteur + " du spot " + spotId + " par " + email);
        spotServiceInterface.supprLongueur(idLongueur,idVoie);
        majModel(model,spotId,httpSession);
        model.addAttribute("secteur", spotServiceInterface.getSecteur(idSecteur));
        model.addAttribute("voie", spotServiceInterface.getVoie(idVoie));
        return "voie-longueur";
    }


    private User recupUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userServiceInterface.findUserByEmail(auth.getName());
    }

    private void majModel(Model model, Long spotId, HttpSession httpSession) {
        User user = recupUser();
        if (user != null) {
            httpSession.setAttribute("utilisateurSession", user);
            model.addAttribute("membre", estMembre(user));
            model.addAttribute("user", user);
            model.addAttribute("roles", user.getRoles());
        } else {
            User userVide = new User();
            model.addAttribute("user", userVide);
        }
        if (spotId != null) {
            Spot spot = spotServiceInterface.findSpot(spotId);
            model.addAttribute("spot", spot);
        }
    }

    private boolean estMembre (User user) {
        boolean membre = false;
        if (user != null) {
            Set<Role> roles = user.getRoles();
            for (Role role : roles) {
                if (role.getRole().equals("MEMBRE")) { membre = true; }
            }
        }
        return membre;
    }

}
