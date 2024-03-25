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
import rva.services.BankaService;

@RestController
public class BankaController {

	@Autowired
	private BankaService service;
	
	@GetMapping("/banka")
	//@RequestMapping(method = RequestMethod.GET, path = "/banka")
	public List<Banka> getAllBankas(){
		return service.getAll();
	}
	
	@GetMapping("/banka/id/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable int id){
		Optional<Banka> banka = service.findById(id);
		if(banka.isPresent()) {
			return ResponseEntity.ok(banka.get());
		}
		return ResponseEntity.status(400).body("Resource with requested ID: " + id +
				"does not exist");
	}
	
	@GetMapping("/banka/naziv/{naziv}")
	public ResponseEntity<?> getBankasByNaziv(@PathVariable String naziv){
		List<Banka> banks = service.getBankasByNazivContainingIgnoreCase(naziv);
		if(banks.isEmpty()) {
			ResponseEntity.status(400).body("Resources with Naziv: " + naziv +
					"do not exist");
			
		}
		return ResponseEntity.ok(banks);
	}
	
	@PostMapping("/banka")
	public ResponseEntity<?> createBanka(@RequestBody Banka banka){
		if(service.existsById(banka.getId())) {
			return ResponseEntity.status(409).body("Resource already exists");
		}
		Banka savedBanka = service.create(banka);
		URI uri = URI.create("/banka/" + savedBanka.getId());
		return ResponseEntity.created(uri).body(savedBanka);
		
	}
	
	@PutMapping("/banka/id/{id}")
	public ResponseEntity<?> updateBanka(@RequestBody Banka banka, @PathVariable int id){
		Optional<Banka> updatedBanka = service.update(banka, id);
		if(updatedBanka.isPresent()) {
			return ResponseEntity.ok(updatedBanka.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id +
				"couldn't be updated because it doesn't exist");
	}
	
	@DeleteMapping("/banka/id/{id}")
	public ResponseEntity<?> deleteBanka(@PathVariable int id){
		if(service.existsById(id)) {
		service.delete(id);
		return ResponseEntity.ok("Resource with ID: " + id + "has been successfully deleted");
	}
		return ResponseEntity.ok("Resource with ID: " + id + 
				 "couldn't be successfully deleted");
}
}
