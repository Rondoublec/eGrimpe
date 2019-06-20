package fr.rbo.archijee.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

public class LivreForm implements Serializable{

	private static final Logger log = LoggerFactory.getLogger(LivreForm.class);

	private String  titre;
	
	private String[] tabGenres;

	public String getTitre() {
		log.info("getTitre", titre);
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
		log.info("setTitre", titre);
	}

	public String[] getTabGenres() {
		log.info("getTabGenres", tabGenres);
		return tabGenres;
	}

	public void setTabGenres(String[] tabGenres) {
		this.tabGenres = tabGenres;
		log.info("setTabGenres", tabGenres);
	}


	
	

}
