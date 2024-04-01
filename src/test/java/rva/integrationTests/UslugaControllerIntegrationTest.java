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

import rva.models.Usluga;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UslugaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Usluga>> response = template.exchange(
				"/usluga",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Usluga>> () {} );
		
		ArrayList<Usluga> list = (ArrayList<Usluga>)response.getBody();
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
	void testGetAllUslugas() {
		ResponseEntity<List<Usluga>> response = template
				.exchange("/usluga", HttpMethod.GET,
							null, 
							new ParameterizedTypeReference<List<Usluga>>() {});
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge = response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!usluge.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetUslugaById() {
		int id = 1;
		
		ResponseEntity<Usluga> response = template
				.exchange("/usluga/id/" + id, HttpMethod.GET,
							null, 
							Usluga.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetUslugaByProvizija() {
		double provizija = 12;
		ResponseEntity<List<Usluga>> response = template
				.exchange("/usluga/provizija/" + provizija, HttpMethod.GET,
						null, 
						new ParameterizedTypeReference<List<Usluga>>() {});
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge = response.getBody();
		
	assertEquals(200, statusCode);
	assertNotNull(usluge.get(0));
	
	for(Usluga u : usluge) {
		assertTrue(u.getProvizija() < provizija);
	}
	}
	
	@Test
	@Order(4)
	void testCreateUsluga() {
		Usluga usluga = new Usluga();
		usluga.setNaziv("POST NAZIV");
		usluga.setOpisUsluge("POST OPIS USLUGE");
		usluga.setProvizija(22);
		usluga.setDatumUgovora(null);
		
		HttpEntity<Usluga> entity = new HttpEntity<Usluga>(usluga);
		createHighestId();
		ResponseEntity<Usluga> response = template
				.exchange("/usluga", HttpMethod.POST, entity, Usluga.class);
		int statusCode = response.getStatusCode().value();
		Usluga createdUsluga = response.getBody();
		
		assertEquals(201, statusCode);
		assertEquals(highestId, createdUsluga.getId());
		assertEquals(usluga.getNaziv(), response.getBody().getNaziv());
		assertEquals(usluga.getOpisUsluge(), response.getBody().getOpisUsluge());
		assertEquals(usluga.getProvizija(), response.getBody().getProvizija());
		assertEquals(usluga.getDatumUgovora(), response.getBody().getDatumUgovora());
		assertEquals("/usluga/" + highestId, response.getHeaders().getLocation().getPath());
	
	}
	
	
	@Test
	@Order(5)
	void testUpdateUsluga() {
		Usluga usluga = new Usluga();
		usluga.setNaziv("POST NAZIV");
		usluga.setOpisUsluge("POST OPIS USLUGE");
		usluga.setProvizija(22);
		usluga.setDatumUgovora(null);
		
		HttpEntity<Usluga> entity = new HttpEntity<Usluga>(usluga);
		getHighestId();
		ResponseEntity<Usluga> response = template
				.exchange("/usluga/id/" + highestId, HttpMethod.PUT, entity, Usluga.class);
		int statusCode = response.getStatusCode().value();
	
		assertEquals(200, statusCode);
		assertTrue(response.getBody() instanceof Usluga);
		assertEquals(usluga.getNaziv(), response.getBody().getNaziv());
		assertEquals(usluga.getOpisUsluge(), response.getBody().getOpisUsluge());
		assertEquals(usluga.getProvizija(), response.getBody().getProvizija());
		assertEquals(usluga.getDatumUgovora(), response.getBody().getDatumUgovora());

	
	}
	
	@Test
	@Order(6)
	void testDeleteUslugaById() {
		getHighestId();
		ResponseEntity<String> response = template
				.exchange("/usluga/id/" + highestId, HttpMethod.DELETE,
						null, String.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted"));

	}
	
	@Test
	@Order(7)
	void testGetUslugaByFilijala() {
		long filijalaId = 1;
		ResponseEntity<List<Usluga>> response = template.exchange("/usluga/filijala/" + filijalaId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Usluga>>(){});
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(usluge.get(0));
		for(Usluga u: usluge) {
			assertTrue(u.getFilijala().getId() == 1);
		}
	}
	@Test
	@Order(8)
	void testGetUslugaByKorisnikUsluge() {
		long korisnikUslugeId = 1;
		ResponseEntity<List<Usluga>> response = template.exchange("/usluga/korisnik_usluge/" + korisnikUslugeId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Usluga>>(){});
		int statusCode = response.getStatusCode().value();
		List<Usluga> usluge =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(usluge.get(0));
		for(Usluga u: usluge) {
			assertTrue(u.getKorisnikUsluge().getId() == 1);
		}
	}
}
