package rva.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Usluga implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "USLUGA_SEQ_GENERATOR", sequenceName="USLUGA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USLUGA_SEQ_GENERATOR")
	private int id;
	private String naziv;
	private String opisUsluge;
	private int provizija;
	private Date datumUgovora;
	
	@ManyToOne
	@JoinColumn(name = "korisnikUsluge")
	private KorisnikUsluge korisnikUsluge;
	
	@ManyToOne
	@JoinColumn(name = "filijala")
	private Filijala filijala;
	
	public Usluga() {
		
	}

	public Usluga(int id, String naziv, String opisUsluge, int provizija, Date datumUgovora) {
		super();
		this.id = id;
		this.naziv = naziv;
		this.opisUsluge = opisUsluge;
		this.provizija = provizija;
		this.datumUgovora = datumUgovora;
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

	public String getOpisUsluge() {
		return opisUsluge;
	}

	public void setOpisUsluge(String opisUsluge) {
		this.opisUsluge = opisUsluge;
	}

	public int getProvizija() {
		return provizija;
	}

	public void setProvizija(int provizija) {
		this.provizija = provizija;
	}

	public Date getDatumUgovora() {
		return datumUgovora;
	}

	public void setDatumUgovora(Date datumUgovora) {
		this.datumUgovora = datumUgovora;
	}
	
	
}
