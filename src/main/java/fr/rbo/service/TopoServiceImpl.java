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
public class TopoServiceImpl implements TopoServiceInterface{

    private static final Logger log = LoggerFactory.getLogger(TopoServiceImpl.class);

    @Autowired
    protected TopoRepository topoRepository;
    @Autowired
    protected TopoRepositoryInterface topoRepositoryInterface;
    @Autowired
    TopoServiceInterface topoServiceInterface;

    public Topo saveTopo(Topo topo, User utilisateur) {
        topo.setProprietaireTopo(utilisateur);
        return topoRepository.save(topo);
    }

    public Boolean deleteTopo(Long topoId) {
        Topo temp = topoRepository.getOne(topoId);
        if(temp!=null){
            topoRepository.delete(temp);
            return true;
        }
        return false;
    }

    public Topo editTopo(Topo topo, User utilisateur) {
        topo.setProprietaireTopo(utilisateur);
        return topoRepository.save(topo);
    }

    public Collection<Topo> getAllTopos() {
        Iterable<Topo> itr = topoRepository.findAll();
        return (Collection<Topo>)itr;
    }

    public Topo findTopo(Long topoId) {
        return topoRepository.getOne(topoId);
    }

    @Override
    public List<Topo> listeTopos(Topo topoCherche) {
        List<Topo> toposTrouve= new ArrayList<>();
        toposTrouve=topoRepositoryInterface.rechercheTopo(topoCherche);
        return toposTrouve;
    }

}
