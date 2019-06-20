package fr.rbo.archijee.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Genre implements Serializable  {

	private static final Logger log = LoggerFactory.getLogger(Genre.class);

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer  id;
	
	private String  code;
	private String  description;

	@ManyToOne
	@JoinColumn(name="livre_id")
	private Livre livre;

	public Genre() {
	
	}


	public Genre(Integer id, String code, String description) {
		super();
		this.id = id;
		this.code = code;
		this.description = description;
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public Genre(String code, String description) {
		super();
		this.code = code;
		this.description = description;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	

	
}
