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

import rva.models.Banka;
import rva.models.Filijala;
import rva.models.Usluga;
import rva.models.KorisnikUsluge;
import rva.services.BankaService;
import rva.services.FilijalaService;
import rva.services.KorisnikUslugeService;
import rva.services.UslugaService;

public class UslugaController {

	@Autowired
	private FilijalaService filijalaService;
	@Autowired
	private KorisnikUslugeService korisnikUslugeService;
	@Autowired
	private UslugaService service;

	
	@GetMapping("/usluga")
	public List<Usluga> getAllUslugas(){
		return service.getAll();
	}
	
	@GetMapping("/usluga/id/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable int id){
		Optional<Usluga> usluga = service.findById(id);
		if(usluga.isPresent()) {
			return ResponseEntity.ok(usluga.get());
		}
		return ResponseEntity.status(400).body("Resource with requested ID: " + id +
				"does not exist");
	}
	
	@GetMapping("/usluga/provizija/{provizija}")
	public ResponseEntity<?> getBankasByNaziv(@PathVariable double provizija){
		List<Usluga> uslugas = service.getUslugasByProvizijaLessThan(provizija);
		if(uslugas.isEmpty()) {
			ResponseEntity.status(400).body("Resources with provizija: " + provizija +
					"do not exist");
			
		}
		return ResponseEntity.ok(uslugas);
	}
	
	@PostMapping("/usluga")
	public ResponseEntity<?> createBanka(@RequestBody Usluga usluga){
		if(service.existsById(usluga.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Usluga savedUsluga = service.create(usluga);
		URI uri = URI.create("/usluga/" + savedUsluga.getId());
		return ResponseEntity.created(uri).body(savedUsluga);
		
	}
	
	@PutMapping("/usluga/id/{id}")
	public ResponseEntity<?> updateUsluga(@RequestBody Usluga usluga, @PathVariable int id){
		Optional<Usluga> updatedUsluga = service.update(usluga, id);
		if(updatedUsluga.isPresent()) {
			return ResponseEntity.ok(updatedUsluga.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id +
				"couldn't be updated because it doesn't exist");
	}
	
	@DeleteMapping("/usluga/id/{id}")
	public ResponseEntity<?> deleteUsluga(@PathVariable int id){
		if(service.existsById(id)) {
		service.delete(id);
		return ResponseEntity.ok("Resource with ID: " + id + "has been successfully deleted");
	}
		return ResponseEntity.ok("Resource with ID: " + id + 
				 "couldn't be successfully deleted");
}
	
	@GetMapping("/usluga/filijala/{foreignKey}")
	public ResponseEntity<?> getUslugeByFilijala(@PathVariable int foreignKey){
		Optional<Filijala> filijala = filijalaService.findById(foreignKey);
		if(filijala.isPresent()) {
			List<Usluga> usluge = service.findByForeignKey(filijala.get());
			if(usluge.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " +
						foreignKey + "do not exist");
			}
			else {
				return ResponseEntity.ok(usluge);
			}
		}
			return ResponseEntity.status(400).body("Invalid foreign key: " + foreignKey);
		}
	
	@GetMapping("/usluga/korisnik_usluge/{foreignKey}")
	public ResponseEntity<?> getUslugeByKorisnikUsluge(@PathVariable int foreignKey){
		Optional<KorisnikUsluge> korisnikUsluge = korisnikUslugeService.findById(foreignKey);
		if(korisnikUsluge.isPresent()) {
			List<Usluga> usluge = service.findByForeignKey(korisnikUsluge.get());
			if(usluge.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " +
						foreignKey + "do not exist");
			}
			else {
				return ResponseEntity.ok(usluge);
			}
		}
			return ResponseEntity.status(400).body("Invalid foreign key: " + foreignKey);
		}
	}

