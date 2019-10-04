package fr.rbo.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

import fr.rbo.model.*;
import fr.rbo.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class SpotServiceImpl implements SpotServiceInterface{

    private static final Logger log = LoggerFactory.getLogger(SpotServiceImpl.class);

    @Autowired
    protected SpotRepository spotRepository;
    @Autowired
    protected SpotRepositoryInterface spotRepositoryInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private CommentaireRepository commentaireRepository;
    @Autowired
    private SecteurRepository secteurRepository;
    @Autowired
    private VoieRepository voieRepository;
    @Autowired
    private LongueurRepository longueurRepository;
    @Autowired
    SpotServiceInterface spotServiceInterface;

    public Spot saveSpot(Spot spot) {
        // TODO Auto-generated method stub
        spot.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        return spotRepository.save(spot);
    }

    public Boolean deleteSpot(Long spotId) {
        // TODO Auto-generated method stub
        Spot temp = spotRepository.getOne(spotId);
        if(temp!=null){
            spotRepository.delete(temp);
            return true;
        }
        return false;
    }

    public Spot editSpot(Spot spot) {
        // TODO Auto-generated method stub
        spot.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        return spotRepository.save(spot);
    }

    public Collection<Spot> getAllSpots() {
        // TODO Auto-generated method stub
        Iterable<Spot> itr = spotRepository.findAll();
        return (Collection<Spot>)itr;
    }

    public Spot findSpot(Long spotId) {
        // TODO Auto-generated method stub
        return spotRepository.getOne(spotId);
    }

    public void ajoutCommentaire(Commentaire commentaire,  Long spotId, String email) {
        Spot spot = spotServiceInterface.findSpot(spotId);
        User user = userServiceInterface.findUserByEmail(email);
        commentaire.setUserCommentaire(user);
        commentaire.setSpot(spot);
        commentaire.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        commentaireRepository.save(commentaire);
        List<Commentaire> commentaires = spot.getCommentaires();
        commentaires.add(commentaire);
        spot.setCommentaires(commentaires);
        spotRepository.save(spot);
    }
    @Override
    public void modifCommentaire(Commentaire commentaire, Long spotId, String email) {
        Spot spot = spotServiceInterface.findSpot(spotId);
        Commentaire commModifie = commentaireRepository.findById(commentaire.getId());
        commModifie.setMessage(commentaire.getMessage());
        commModifie.setUserCommentaire(userServiceInterface.findUserByEmail(email));
        commModifie.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        commentaireRepository.save(commModifie);
        spotRepository.save(spot);
    }
    @Override
    public void supprCommentaire(int idCommentaire, Long spotId) {
        Spot spot = spotServiceInterface.findSpot(spotId);
        Commentaire commentaire=commentaireRepository.findById(idCommentaire);
        commentaireRepository.delete(commentaire);
        spotRepository.save(spot);
    }

    @Override
    public Spot ajoutSecteur(Long spotId, Secteur secteur) {
//        Spot spot = spotRepository.findById(spotId);
        Spot spot = spotServiceInterface.findSpot(spotId);
        Secteur secteurEnreg = new Secteur();
        secteurEnreg.setSpot(spot);
        secteurEnreg.setDescription(secteur.getDescription());
        secteurEnreg.setNom(secteur.getNom());
        secteurEnreg.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        secteurRepository.save(secteurEnreg);
        List<Secteur> secteurs = spot.getSecteurs();
        secteurs.add(secteurEnreg);
        spot.setSecteurs(secteurs);
//        spotRepository.save(spot);
        return spotRepository.save(spot);
    }
    @Override
    public void supprSecteur(int idSecteur, Long spotId) {
        Spot spot = spotServiceInterface.findSpot(spotId);
        Secteur secteur=secteurRepository.findById(idSecteur);
        secteurRepository.delete(secteur);
        spotRepository.save(spot);
    }
    @Override
    public Secteur getSecteur(int idSecteur) {
        return secteurRepository.findById(idSecteur);
    }

    @Override
    public Secteur ajoutVoie(int idSecteur, Voie voie) {
        Secteur secteur = secteurRepository.findById(idSecteur);
        Voie voieEnreg = new Voie();
        voieEnreg.setSecteur(secteur);
        voieEnreg.setDescription(voie.getDescription());
        voieEnreg.setNom(voie.getNom());
        voieEnreg.setCotation(voie.getCotation());
        voieEnreg.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        voieRepository.save(voieEnreg);
        List<Voie> voies = secteur.getVoies();
        voies.add(voieEnreg);
        secteur.setVoies(voies);
        return secteurRepository.save(secteur);
    }
    @Override
    public Voie getVoie(int idVoie) {
        return voieRepository.findById(idVoie);
    }
    @Override
    public void supprVoie(int idVoie, int idSecteur) {
        Secteur secteur=secteurRepository.findById(idSecteur);
        Voie voie=voieRepository.findById(idVoie);
        voieRepository.delete(voie);
        secteurRepository.save(secteur);
    }

    @Override
    public Voie ajoutLongueur(int idVoie, Longueur longueur) {
        Voie voie = voieRepository.findById(idVoie);
        Longueur longueurEnreg = new Longueur();
        longueurEnreg.setVoie(voie);
        longueurEnreg.setDescription(longueur.getDescription());
        longueurEnreg.setNom(longueur.getNom());
        longueurEnreg.setCotation(longueur.getCotation());
        longueurEnreg.setDateDeMiseAJour(new Timestamp(System.currentTimeMillis()));
        longueurRepository.save(longueurEnreg);
        List<Longueur> longueurs = voie.getLongueurs();
        longueurs.add(longueurEnreg);
        voie.setLongueurs(longueurs);
        return voieRepository.save(voie);
    }
    @Override
    public Longueur getLongueur(int idLongueur) {
        return longueurRepository.findById(idLongueur);
    }
    @Override
    public void supprLongueur(int idLongueur, int idVoie) {
        Voie voie=voieRepository.findById(idVoie);
        Longueur longueur=longueurRepository.findById(idLongueur);
        longueurRepository.delete(longueur);
        voieRepository.save(voie);
    }

    @Override
    public List<Spot> chercheSpots(Spot spotCherche) {
        List<Spot> spotsTrouve= new ArrayList<>();
        spotsTrouve=spotRepositoryInterface.rechercheSpotMultiCriteres(spotCherche);
        return spotsTrouve;
    }

}
