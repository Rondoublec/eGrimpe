package fr.rbo.controller;

import java.util.List;
import java.util.Set;

import fr.rbo.model.*;
import fr.rbo.service.TopoServiceInterface;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Controleur de la gestion des topos
 */
@Controller
public class TopoController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);
    private String referer = "topo";

    @Autowired
    TopoServiceInterface topoServiceInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;

    /**
     * Affiche le liste de toutes les topos
     * @param model
     * @param httpSession
     * @param request
     * @return
     */
    @GetMapping({"/topo", "/lestopos"})
    public String Topo(Model model, HttpSession httpSession, HttpServletRequest request) {
        log.debug("page rechercher les topos");
        String url = request.getRequestURI();
        if (url.equals("/lestopos")){
            referer = "topo";
        }
        Topo topoCriteres = new Topo();
        if (referer.equals("mestopos")) {
            User user = recupUser();
            if (user != null) { topoCriteres.setProprietaireTopo(user);}
        } else {
            referer = "topo";
        }
        List<Topo> listTopos= topoServiceInterface.listeTopos(topoCriteres);
        model.addAttribute("topoCriteres", topoCriteres);
        model.addAttribute("listTopos", listTopos);
        majModel(model,null, httpSession);
        return "recherche-topo-list";
    }

    /**
     * Affiche la liste des topos de l'utilisateur connecté
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/mestopos")
    public String MesTopos(Model model, HttpSession httpSession) {
        log.debug("page mes topos");
        Topo topoCriteres = new Topo();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String mailUser = auth.getName();
        User user = userServiceInterface.findUserByEmail(mailUser);
        if (user != null) {
            topoCriteres.setProprietaireTopo(user);
        }
        List<Topo> listTopos= topoServiceInterface.listeTopos(topoCriteres);
        model.addAttribute("topoCriteres", topoCriteres);
        model.addAttribute("listTopos", listTopos);
        majModel(model,null,httpSession);
        referer = "mestopos";
        return "recherche-topo-list";
    }

    /**
     * Lance une recherche et affiche la liste des topos correspondantes aux critères saisies dans le formulaire
     * @param model
     * @param topoCriteres
     * @param httpSession
     * @return
     */
    @PostMapping(value = "/topo/recherche")
    public String TopoeRecherche (Model model, @ModelAttribute ("topoCriteres") Topo topoCriteres,
                                  HttpSession httpSession) {
        log.debug("lancement d'une recherche");
        if (referer.equals("mestopos")) {
            User user = recupUser();
            if (user != null) { topoCriteres.setProprietaireTopo(user);}
        }
        List<Topo> listTopos= topoServiceInterface.listeTopos(topoCriteres);
        model.addAttribute("topoCriteres", topoCriteres);
        model.addAttribute("listTopos", listTopos);
        majModel(model,null,httpSession);
        return "recherche-topo-list";
    }

    /**
     * Appel le formulaire de création d'une topo
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/topo/add")
    public String addTopo(Model model, HttpSession httpSession) {
        Topo addTopo = new Topo();
        model.addAttribute("topo", addTopo);
        majModel(model,null,httpSession);
        return "topo-form";
    }

    /**
     * Supprime la topo correspondante à topoId
     * @param topoId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/topo/delete/{topoId}")
    public String RemoveTopo(@PathVariable("topoId") Long topoId,
                             final RedirectAttributes redirectAttributes) {
        String email = recupUser().getEmail();
        if (!estMembre(recupUser())) {
            log.info("ALERTE SECURITE tentative de suppression topo : " + topoId + " par " + email + " NON AUTORISEE");
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            return pageOrigine();
        }
        log.info("Suppression topo : " + topoId + " par " + email);
        if (topoServiceInterface.deleteTopo(topoId)) {
            redirectAttributes.addFlashAttribute("deletion", "success");
        } else {
            redirectAttributes.addFlashAttribute("deletion", "unsuccess");
        }
        return pageOrigine();
    }
    /**
     * Met à jour les informations de demande de prêt de la topo correspondante à topoId
     * Lors de la demande d'emprunt d'une topo
     * @param topoId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/topo/demande/{topoId}")
    public String EmprunterTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User utilisateur = userServiceInterface.findUserByEmail(auth.getName());
        if (topoServiceInterface.emprunterTopo(topoId, utilisateur)){
            redirectAttributes.addFlashAttribute("soumettreDemande", "success");
        } else {
            redirectAttributes.addFlashAttribute("soumettreDemande", "unsuccess");
        }
        return pageOrigine();
    }

    /**
     * Met à jour les informations de disponibilité de la topo correspondante à topoId
     * Lors de l'acceptationdu prêt de la topo
     * @param topoId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/topo/accepte/{topoId}")
    public String AccepterTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes) {
        if (!proprietaireTopo(topoId)){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            return pageOrigine();
        }
        if (topoServiceInterface.accepterEmpruntTopo(topoId)){
            redirectAttributes.addFlashAttribute("accepterDemande", "success");
        } else {
            redirectAttributes.addFlashAttribute("accepterDemande", "unsuccess");
        }
        return pageOrigine();
    }

    /**
     * Met à jour les informations de disponibilité de la topo correspondante à topoId
     * refus ou restitution de prêt d'un topo
     * @param topoId
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/topo/annule/{topoId}")
    public String AnnulerTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes) {
        if (!proprietaireTopo(topoId)){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            return pageOrigine();
        }
        if (topoServiceInterface.annulerDemandeTopo(topoId)){
            redirectAttributes.addFlashAttribute("annulerDemande", "success");
        } else {
            redirectAttributes.addFlashAttribute("annulerDemande", "unsuccess");
        }
        return pageOrigine();
    }

    /**
     * Affiche le formulaire de modification des information d'une topo
     * correspondante à topoId
     * @param topoId
     * @param redirectAttributes
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/topo/edit/{topoId}")
    public String EditTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                           Model model, HttpSession httpSession) {
        if (!proprietaireTopo(topoId)){
            redirectAttributes.addFlashAttribute("status","notAuthorize");
            return pageOrigine();
        }
        Topo editTopo = topoServiceInterface.findTopo(topoId);
        if(editTopo!=null) {
            majModel(model,null,httpSession);
            model.addAttribute("topo", editTopo);
            return "topo-form";
        } else {
            redirectAttributes.addFlashAttribute("status","notfound");
        }
        return pageOrigine();
    }

    /**
     * Affiche les information d'une topo correspondant à topoId
     * @param topoId
     * @param redirectAttributes
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/topo/affiche/{topoId}")
    public String AfficheTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                           Model model, HttpSession httpSession) {
        Topo afficheTopo = topoServiceInterface.findTopo(topoId);
        if(afficheTopo!=null) {
            majModel(model,null,httpSession);
            model.addAttribute("topo", afficheTopo);
            return "topo";
        } else {
            redirectAttributes.addFlashAttribute("status","notfound");
        }
        return pageOrigine();
    }

    /**
     * Enregistre les information d'une topo
     * En création et modification
     * @param model
     * @param topo
     * @param bindingResult
     * @param httpSession
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/topo/save")
    public String saveTopo(Model model, @ModelAttribute("topo") @Valid Topo topo,
                           BindingResult bindingResult, HttpSession httpSession,
                           final RedirectAttributes redirectAttributes) {
        // Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
        if (bindingResult.hasErrors()) {
            majModel(model,null,httpSession);
            return "topo-form"; // Formulaire en cours sur lequel on veut rester
        }
        if(topoServiceInterface.saveTopo(topo, recupUser())!=null) {
            redirectAttributes.addFlashAttribute("saveTopo", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveTopo", "unsuccess");
        }
        return pageOrigine();
    }

    private User recupUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userServiceInterface.findUserByEmail(auth.getName());
    }

    private boolean proprietaireTopo(Long topoId) {
        return (SecurityContextHolder.getContext().getAuthentication().getName().equals(topoServiceInterface.findTopo(topoId).getProprietaireTopo().getEmail()));
    }

    private void majModel(Model model, Long topoId, HttpSession httpSession) {
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
        if (topoId != null) {
            Topo topo = topoServiceInterface.findTopo(topoId);
            model.addAttribute("topo", topo);
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

    private String pageOrigine() {
        String page = "redirect:/topo";
        if (referer == "mestopos") { page = "redirect:/" + referer; }
        return page;
    }

}
