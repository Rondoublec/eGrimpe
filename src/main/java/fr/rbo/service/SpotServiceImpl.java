package fr.rbo.service;

import java.util.Collection;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.rbo.model.Spot;
import fr.rbo.repository.SpotRepository;
import fr.rbo.model.Commentaire;
import fr.rbo.repository.CommentaireRepository;
import fr.rbo.model.User;
import fr.rbo.repository.UserRepository;


@Service
@Transactional
public class SpotServiceImpl implements SpotServiceInterface{

    private static final Logger log = LoggerFactory.getLogger(SpotServiceImpl.class);

    @Autowired
    protected SpotRepository spotRepository;
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private CommentaireRepository commentaireRepository;
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

}
