package rva.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import rva.models.KorisnikUsluge;

@Service
public interface KorisnikUslugeService extends CrudService<KorisnikUsluge> {

	List<KorisnikUsluge> getKorisnikUslugesByMaticniBrojContainingIgnoreCase(String maticniBroj);

}
