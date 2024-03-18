package rva.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import rva.models.Filijala;
import rva.models.Usluga;
import rva.models.KorisnikUsluge;
import rva.services.FilijalaService;
import rva.services.KorisnikUslugeService;
import rva.services.UslugaService;

public class UslugaController {

	@Autowired
	private FilijalaService filijalaService;
	@Autowired
	private UslugaService service;

	
	@GetMapping("/usluga/filijala/{foreignKey")
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
	}

