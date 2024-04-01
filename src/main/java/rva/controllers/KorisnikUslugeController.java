package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.models.KorisnikUsluge;
import rva.services.KorisnikUslugeService;

@RestController
public class KorisnikUslugeController {

	@Autowired
	private KorisnikUslugeService service;
	
	@GetMapping("/korisnikUsluge")
	//@RequestMapping(method = RequestMethod.GET, path = "/korisnikUsluge")
	public List<KorisnikUsluge> getAllKorisnikUsluges(){
		return service.getAll();
	}
	
	@GetMapping("/korisnikUsluge/id/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable int id){
		Optional<KorisnikUsluge> korisnikUsluge = service.findById(id);
		if(korisnikUsluge.isPresent()) {
			return ResponseEntity.ok(korisnikUsluge.get());
		}
		return ResponseEntity.status(400).body("Resource with requested ID: " + id +
				"does not exist");
	}
	
	@GetMapping("/korisnikUsluge/maticniBroj/{maticniBroj}")
	public ResponseEntity<?> getKorisnikUslugesByNaziv(@PathVariable String maticniBroj){
		List<KorisnikUsluge> korisnik = service.getKorisnikUslugesByMaticniBrojContainingIgnoreCase(maticniBroj);
		if(korisnik.isEmpty()) {
			return ResponseEntity.status(400).body("Resources with maticni broj: " + maticniBroj +
					"do not exist");
			
		}
		return ResponseEntity.ok(korisnik);
	}
	
	@PostMapping("/korisnikUsluge")
	public ResponseEntity<?> createKorisnikUsluge(@RequestBody KorisnikUsluge korisnikUsluge){
		if(service.existsById(korisnikUsluge.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		KorisnikUsluge savedKorisnikUsluge = service.create(korisnikUsluge);
		URI uri = URI.create("/korisnikUsluge/" + savedKorisnikUsluge.getId());
		return ResponseEntity.created(uri).body(savedKorisnikUsluge);
		
	}
	
	@PutMapping("/korisnikUsluge/id/{id}")
	public ResponseEntity<?> updateKorisnikUsluge(@RequestBody KorisnikUsluge korisnikUsluge, @PathVariable int id){
		Optional<KorisnikUsluge> updatedKorisnikUsluge = service.update(korisnikUsluge, id);
		if(updatedKorisnikUsluge.isPresent()) {
			return ResponseEntity.ok(updatedKorisnikUsluge.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id +
				"couldn't be updated because it doesn't exist");
	}
	
	@DeleteMapping("/korisnikUsluge/id/{id}")
	public ResponseEntity<?> deleteKorisnikUsluge(@PathVariable int id){
		if(service.existsById(id)) {
		service.delete(id);
		return ResponseEntity.ok("Resource with ID: " + id + "has been successfully deleted");
	}
		return ResponseEntity.ok("Resource with ID: " + id + 
				 "couldn't be successfully deleted");
}
}
