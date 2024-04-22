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

import rva.models.Filijala;
import rva.models.Usluga;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilijalaIntegrationTest {


	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Filijala>> response = template.exchange(
				"/filijala",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Filijala>> () {} );
		
		ArrayList<Filijala> list = (ArrayList<Filijala>)response.getBody();
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
	void testGetAllFilijalas() {
		ResponseEntity<List<Filijala>> response = template
				.exchange("/filijala", HttpMethod.GET,
							null, 
							new ParameterizedTypeReference<List<Filijala>>() {});
		int statusCode = response.getStatusCode().value();
		List<Filijala> usluge = response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!usluge.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetFilijalaById() {
		int id = 1;
		
		ResponseEntity<Filijala> response = template
				.exchange("/filijala/id/" + id, HttpMethod.GET,
							null, 
							Filijala.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetFilijalaBySef() {
		boolean sef = true; 
		
		ResponseEntity<List<Filijala>> response = template
				.exchange("/filijala/sef/" + sef, HttpMethod.GET,
						null, 
						new ParameterizedTypeReference<List<Filijala>>() {});
		int statusCode = response.getStatusCode().value();
		List<Filijala> filijale = response.getBody();
		
	assertEquals(200, statusCode);
	assertNotNull(filijale.get(0));
	
	for(Filijala f : filijale) {
		assertTrue(f.isPosedujeSef() == sef);
	}
	}
	
	@Test
	@Order(4)
	void testCreateFilijala() {
		Filijala filijala = new Filijala();
		filijala.setAdresa("POST ADRESA");
		filijala.setPosedujeSef(true);
		filijala.setBrojPultova(1);
		
		HttpEntity<Filijala> entity = new HttpEntity<Filijala>(filijala);
		createHighestId();
		ResponseEntity<Filijala> response = template
				.exchange("/filijala", HttpMethod.POST, entity, Filijala.class);
		int statusCode = response.getStatusCode().value();
		Filijala createdFilijala = response.getBody();
		
		assertEquals(201, statusCode);
		assertEquals(highestId, createdFilijala.getId());
		assertEquals(filijala.getAdresa(), response.getBody().getAdresa());
		assertEquals(filijala.getBrojPultova(), response.getBody().getBrojPultova());
		assertEquals(filijala.isPosedujeSef(), response.getBody().isPosedujeSef());
		assertEquals("/filijala/" + highestId, response.getHeaders().getLocation().getPath());
	
	}
	
	
	@Test
	@Order(5)
	void testUpdateFilijala() {
		Filijala filijala = new Filijala();
		filijala.setAdresa("PUT ADRESA");
		filijala.setPosedujeSef(true);
		filijala.setBrojPultova(1);
		
		HttpEntity<Filijala> entity = new HttpEntity<Filijala>(filijala);
		getHighestId();
		ResponseEntity<Filijala> response = template
				.exchange("/filijala/id/" + highestId, HttpMethod.PUT, entity, Filijala.class);
		int statusCode = response.getStatusCode().value();
	
		assertEquals(200, statusCode);
		assertTrue(response.getBody() instanceof Filijala);
		assertEquals(filijala.getAdresa(), response.getBody().getAdresa());
		assertEquals(filijala.getBrojPultova(), response.getBody().getBrojPultova());
		assertEquals(filijala.isPosedujeSef(), response.getBody().isPosedujeSef());
	
	}
	
	@Test
	@Order(6)
	void testDeleteFilijalaById() {
		getHighestId();
		ResponseEntity<String> response = template
				.exchange("/filijala/id/" + highestId, HttpMethod.DELETE,
						null, String.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted"));

	}
	@Test
	@Order(7)
	void testGetFilijalaByBanka() {
		long bankaId = 1;
		ResponseEntity<List<Filijala>> response = template.exchange("/filijala/banka/" + bankaId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Filijala>>(){});
		int statusCode = response.getStatusCode().value();
		List<Filijala> filijale =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(filijale.get(0));
		for(Filijala f: filijale) {
			assertTrue(f.getBanka().getId() == 1);
		}
	}
	

}
