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

@Controller
public class TopoController {

    private static final Logger log = LoggerFactory.getLogger(TopoController.class);
    private String referer = "topo";

    @Autowired
    TopoServiceInterface topoServiceInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;

    @GetMapping({"/topo", "/lestopos"})
    public String Topo(Model model, HttpSession httpSession, HttpServletRequest request) {
        log.debug("page rechercher les topos");
        String url = request.getRequestURI();
        if (url.equals("/lestopos")){
            referer = "topo";
        }
        Topo topoCriteres = new Topo();
        if (referer == "mestopos") {
            User user = recupUser(httpSession);
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

    @PostMapping(value = "/topo/recherche")
    public String TopoeRecherche (Model model, @ModelAttribute ("topoCriteres") Topo topoCriteres,
                                  HttpSession httpSession) {
        log.debug("lancement d'une recherche");
        if (referer == "mestopos") {
            User user = recupUser(httpSession);
            if (user != null) { topoCriteres.setProprietaireTopo(user);}
        }
        List<Topo> listTopos= topoServiceInterface.listeTopos(topoCriteres);
        model.addAttribute("topoCriteres", topoCriteres);
        model.addAttribute("listTopos", listTopos);
        majModel(model,null,httpSession);
        return "recherche-topo-list";
    }

    @GetMapping("/topo/add")
    public String addTopo(Model model, HttpSession httpSession) {
        Topo addTopo = new Topo();
        model.addAttribute("topo", addTopo);
        majModel(model,null,httpSession);
        return "topo-form";
    }

    @GetMapping("/topo/delete/{topoId}")
    public String RemoveTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                             Model model) {
        if (topoServiceInterface.deleteTopo(topoId)) {
            redirectAttributes.addFlashAttribute("deletion", "success");
        } else {
            redirectAttributes.addFlashAttribute("deletion", "unsuccess");
        }
        return pageOrigine();
    }

    @GetMapping("/topo/demande/{topoId}")
    public String EmprunterTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                           Model model, HttpSession httpSession) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User utilisateur = userServiceInterface.findUserByEmail(auth.getName());
        if (topoServiceInterface.emprunterTopo(topoId, utilisateur)){
            redirectAttributes.addFlashAttribute("demande", "success");
        } else {
            redirectAttributes.addFlashAttribute("demande", "unsuccess");
        }
        return pageOrigine();
    }

    @GetMapping("/topo/accepte/{topoId}")
    public String AccepterTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                                Model model, HttpSession httpSession) {
        if (topoServiceInterface.accepterEmpruntTopo(topoId)){
            redirectAttributes.addFlashAttribute("valideDemande", "success");
        } else {
            redirectAttributes.addFlashAttribute("valideDemande", "unsuccess");
        }
        return pageOrigine();
    }

    @GetMapping("/topo/annule/{topoId}")
    public String AnnulerrTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                                Model model, HttpSession httpSession) {
        if (topoServiceInterface.annulerDemandeTopo(topoId)){
            redirectAttributes.addFlashAttribute("annuleDemande", "success");
        } else {
            redirectAttributes.addFlashAttribute("annuleDemande", "unsuccess");
        }
        return pageOrigine();
    }

    @GetMapping("/topo/edit/{topoId}")
    public String EditTopo(@PathVariable("topoId") Long topoId, final RedirectAttributes redirectAttributes,
                           Model model, HttpSession httpSession) {
        Topo editTopo = topoServiceInterface.findTopo(topoId);
        if(editTopo!=null) {
            majModel(model,null,httpSession);
            model.addAttribute("topo", editTopo);
            return "topo-form";
        } else {
            redirectAttributes.addFlashAttribute("status","notfound");
        }
        return "redirect:/topo";
    }

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

    @PostMapping("/topo/save")
    public String saveTopo(Model model, @ModelAttribute("topo") @Valid Topo topo,
                           BindingResult bindingResult, HttpSession httpSession,
                           final RedirectAttributes redirectAttributes) {

        // Si erreur de validation par rapport aux annotations de validation de l'objet au niveau de sa declaration
        if (bindingResult.hasErrors()) {
            majModel(model,null,httpSession);
            return "topo-form"; // Formulaire en cours sur lequel on veut rester
        }

        if(topoServiceInterface.saveTopo(topo, recupUser(httpSession))!=null) {
            redirectAttributes.addFlashAttribute("saveTopo", "success");
        } else {
            redirectAttributes.addFlashAttribute("saveTopo", "unsuccess");
        }
        return pageOrigine();
    }

    private User recupUser(HttpSession httpSession){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userServiceInterface.findUserByEmail(auth.getName());

    }

    private void majModel(Model model, Long topoId, HttpSession httpSession) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String mailUser = auth.getName();
        User user = userServiceInterface.findUserByEmail(mailUser);

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
                if (role.getRole().equals("MEMBRE")) {
                    membre = true;
                }
            }
        }
        return membre;
    }

    private String pageOrigine() {
        String page = "redirect:/topo";
        if (referer == "mestopos") {
            page = "redirect:/" + referer;
        }
        return page;
    }

}
