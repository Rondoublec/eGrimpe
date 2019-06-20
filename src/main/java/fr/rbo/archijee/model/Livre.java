package fr.rbo.archijee.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Livre implements Serializable {

	private static final Logger log = LoggerFactory.getLogger(Livre.class);

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String  titre;

	@OneToMany(mappedBy="livre",cascade = CascadeType.ALL)
	private List<Genre> genres;

	public Livre() {
		
	}

	public Livre(String titre, List<Genre> genres) {
		super();
		this.titre = titre;
		this.genres = genres;
	}


	public Livre(String titre) {
		super();
		this.titre = titre;
	}




	public List<Genre> getGenres() {
		return genres;
	}




	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String  getAllGenres() {
		String lesgenres ="";
		for(Genre genre : genres){
			lesgenres += " -"+genre.getDescription();
			System.out.println(lesgenres);
		}

		return lesgenres;
	}

	
	
	
	

}
