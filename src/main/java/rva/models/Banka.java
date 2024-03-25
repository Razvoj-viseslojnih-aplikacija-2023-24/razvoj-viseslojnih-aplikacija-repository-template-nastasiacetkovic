package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Banka implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "BANKA_SEQ_GENERATOR", sequenceName="BANKA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BANKA_SEQ_GENERATOR")
	private int id;
	private String naziv;
	private String kontakt;
	private int PIB;
	
	@JsonIgnore
	@OneToMany(mappedBy = "banka", cascade = CascadeType.REMOVE)
	private List<Filijala> filijale;
	
	public Banka() {
		
	}


	public Banka(int id, String naziv, String kontakt, int pIB) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.kontakt = kontakt;
		PIB = pIB;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNaziv() {
		return naziv;
	}


	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}


	public String getKontakt() {
		return kontakt;
	}


	public void setKontakt(String kontakt) {
		this.kontakt = kontakt;
	}


	public int getPIB() {
		return PIB;
	}


	public void setPIB(int pIB) {
		PIB = pIB;
	}
	
	
}
