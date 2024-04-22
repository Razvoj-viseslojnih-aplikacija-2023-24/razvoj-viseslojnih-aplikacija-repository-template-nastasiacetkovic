package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.KorisnikUsluge;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class KorisnikUslugeIntegrationTest {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<KorisnikUsluge>> response = template.exchange(
				"/korisnikUsluge",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<KorisnikUsluge>> () {} );
		
		ArrayList<KorisnikUsluge> list = (ArrayList<KorisnikUsluge>)response.getBody();
		for(int i = 0; i < list.size(); i ++) {
			if(highestId <= list.get(i).getId()) {
				highestId = list.get(i).getId() + 1;
			}
		}
	}
	void getHighestId() {
		createHighestId();
		highestId--;
	}
	
	@Test
	@Order(1)
	void testGetAllKorisnikUsluges() {
		ResponseEntity<List<KorisnikUsluge>> response = template
				.exchange("/korisnikUsluge", HttpMethod.GET,
							null, 
							new ParameterizedTypeReference<List<KorisnikUsluge>>() {});
		int statusCode = response.getStatusCode().value();
		List<KorisnikUsluge> korisnik = response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!korisnik.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetKorisnikUslugeById() {
		int id = 1;
		
		ResponseEntity<KorisnikUsluge> response = template
				.exchange("/korisnikUsluge/id/" + id, HttpMethod.GET,
							null, 
							KorisnikUsluge.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetKorisnikUslugeByMaticniBroj() {
		String maticniBroj = "2410002805147";
		
		ResponseEntity<List<KorisnikUsluge>> response = template
				.exchange("/korisnikUsluge/maticniBroj/" + maticniBroj, HttpMethod.GET,
						null, 
						new ParameterizedTypeReference<List<KorisnikUsluge>>() {});
		int statusCode = response.getStatusCode().value();
		List<KorisnikUsluge> korisnik = response.getBody();
		
	assertEquals(200, statusCode);
	assertNotNull(korisnik.get(0));
	
	for(KorisnikUsluge k : korisnik) {
		assertTrue(k.getMaticniBroj().equals(maticniBroj));
	}
	}
	
	@Test
	@Order(4)
	void testCreateKorisnikUsluge() {
		KorisnikUsluge korisnikUsluge = new KorisnikUsluge();
		korisnikUsluge.setIme("POST IME");
		korisnikUsluge.setPrezime("POST PREZIME");
		korisnikUsluge.setMaticniBroj("1234567891011");
		
		HttpEntity<KorisnikUsluge> entity = new HttpEntity<KorisnikUsluge>(korisnikUsluge);
		createHighestId();
		ResponseEntity<KorisnikUsluge> response = template
				.exchange("/korisnikUsluge", HttpMethod.POST, entity, KorisnikUsluge.class);
		int statusCode = response.getStatusCode().value();
		KorisnikUsluge createdKorisnikUsluge = response.getBody();
		
		assertEquals(201, statusCode);
		assertEquals(highestId, createdKorisnikUsluge.getId());
		assertEquals(korisnikUsluge.getIme(), response.getBody().getIme());
		assertEquals(korisnikUsluge.getPrezime(), response.getBody().getPrezime());
		assertEquals(korisnikUsluge.getMaticniBroj(), response.getBody().getMaticniBroj());
		assertEquals("/korisnikUsluge/" + highestId, response.getHeaders().getLocation().getPath());
	
	}
	
	
	@Test
	@Order(5)
	void testUpdateKorisnikUsluge() {
		KorisnikUsluge korisnikUsluge = new KorisnikUsluge();
		korisnikUsluge.setIme("PUT IME");
		korisnikUsluge.setPrezime("PUT PREZIME");
		korisnikUsluge.setMaticniBroj("1234567891012");
		
		HttpEntity<KorisnikUsluge> entity = new HttpEntity<KorisnikUsluge>(korisnikUsluge);
		getHighestId();
		ResponseEntity<KorisnikUsluge> response = template
				.exchange("/korisnikUsluge/id/" + highestId, HttpMethod.PUT, entity, KorisnikUsluge.class);
		int statusCode = response.getStatusCode().value();
	
		assertEquals(200, statusCode);
		assertTrue(response.getBody() instanceof KorisnikUsluge);
		assertEquals(korisnikUsluge.getIme(), response.getBody().getIme());
		assertEquals(korisnikUsluge.getPrezime(), response.getBody().getPrezime());
		assertEquals(korisnikUsluge.getMaticniBroj(), response.getBody().getMaticniBroj());
	
	}
	
	@Test
	@Order(6)
	void testDeleteKorisnikUslugeById() {
		getHighestId();
		ResponseEntity<String> response = template
				.exchange("/korisnikUsluge/id/" + highestId, HttpMethod.DELETE,
						null, String.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted"));

	}
	

}
