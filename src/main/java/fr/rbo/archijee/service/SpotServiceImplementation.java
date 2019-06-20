package fr.rbo.archijee.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.rbo.archijee.model.Spot;
import fr.rbo.archijee.repository.SpotRepository;


@Service
@Transactional
public class SpotServiceImplementation implements SpotServiceInterface{

    private static final Logger log = LoggerFactory.getLogger(SpotServiceImplementation.class);

    @Autowired
    protected SpotRepository spotRepository;

    public Spot saveSpot(Spot spot) {
        // TODO Auto-generated method stub
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


}
