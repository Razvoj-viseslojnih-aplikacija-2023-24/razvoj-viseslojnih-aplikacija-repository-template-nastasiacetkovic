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

import rva.models.Banka;
import rva.models.Filijala;
import rva.models.KorisnikUsluge;
import rva.models.Filijala;
import rva.models.Usluga;
import rva.services.BankaService;
import rva.services.FilijalaService;
import rva.services.FilijalaService;
import rva.services.UslugaService;

@RestController
public class FilijalaController {

	@Autowired
	private FilijalaService service;
	
	@Autowired
	private BankaService bankaService;
	
	@GetMapping("/filijala")
	//@RequestMapping(method = RequestMethod.GET, path = "/filijala")
	public List<Filijala> getAllFilijalas(){
		return service.getAll();
	}
	
	@GetMapping("/filijala/id/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable int id){
		Optional<Filijala> filijala = service.findById(id);
		if(filijala.isPresent()) {
			return ResponseEntity.ok(filijala.get());
		}
		return ResponseEntity.status(400).body("Resource with requested ID: " + id +
				"does not exist");
	}
	
	@GetMapping("/filijala/sef/{sef}")
	public ResponseEntity<?> getFilijalasBySef(@PathVariable boolean sef){
		List<Filijala> filijalas = service.getFilijalasByPosedujeSefEquals(sef);
		if(filijalas.isEmpty()) {
			ResponseEntity.status(400).body("Resources with sef: " + sef +
					"do not exist");
			
		}
		return ResponseEntity.ok(filijalas);
	}
	
	@PostMapping("/filijala")
	public ResponseEntity<?> createFilijala(@RequestBody Filijala filijala){
		if(service.existsById(filijala.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Filijala savedFilijala = service.create(filijala);
		URI uri = URI.create("/filijala/" + savedFilijala.getId());
		return ResponseEntity.created(uri).body(savedFilijala);
		
	}
	
	@PutMapping("/filijala/id/{id}")
	public ResponseEntity<?> updateFilijala(@RequestBody Filijala filijala, @PathVariable int id){
		Optional<Filijala> updatedFilijala = service.update(filijala, id);
		if(updatedFilijala.isPresent()) {
			return ResponseEntity.ok(updatedFilijala.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id +
				"couldn't be updated because it doesn't exist");
	}
	
	@DeleteMapping("/filijala/id/{id}")
	public ResponseEntity<?> deleteFilijala(@PathVariable int id){
		if(service.existsById(id)) {
		service.delete(id);
		return ResponseEntity.ok("Resource with ID: " + id + "has been successfully deleted");
	}
		return ResponseEntity.ok("Resource with ID: " + id + 
				 "couldn't be successfully deleted");
}
	@GetMapping("/filijala/banka/{foreignKey}")
	public ResponseEntity<?> getUslugeByKorisnikUsluge(@PathVariable int foreignKey){
		Optional<Banka> banke = bankaService.findById(foreignKey);
		if(banke.isPresent()) {
			List<Filijala> filijala = service.findByForeignKey(banke.get());
			if(filijala.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " +
						foreignKey + "do not exist");
			}
			else {
				return ResponseEntity.ok(filijala);
			}
		}
			return ResponseEntity.status(400).body("Invalid foreign key: " + foreignKey);
		}
	
}
