package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import rva.models.Banka;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BankaControllerIntegrationTest {

	@Autowired
	TestRestTemplate template;
	
	int highestId;
	
	void createHighestId() {
		ResponseEntity<List<Banka>> response = template.exchange(
				"/banka",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Banka>> () {} );
		
		ArrayList<Banka> list = (ArrayList<Banka>)response.getBody();
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
	void testGetAllBankas() {
		ResponseEntity<List<Banka>> response = template
				.exchange("/banka", HttpMethod.GET,
							null, 
							new ParameterizedTypeReference<List<Banka>>() {});
		int statusCode = response.getStatusCode().value();
		List<Banka> banke = response.getBody();
		
		assertEquals(200, statusCode);
		assertTrue(!banke.isEmpty());
	}
	
	@Test
	@Order(2)
	void testGetBankaById() {
		int id = 1;
		
		ResponseEntity<Banka> response = template
				.exchange("/banka/id/" + id, HttpMethod.GET,
							null, 
							Banka.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertEquals(id, response.getBody().getId());
	}
	
	@Test
	@Order(3)
	void testGetBankaByNaziv() {
		String naziv = "Unicredit";
		
		ResponseEntity<List<Banka>> response = template
				.exchange("/banka/naziv/" + naziv, HttpMethod.GET,
						null, 
						new ParameterizedTypeReference<List<Banka>>() {});
		int statusCode = response.getStatusCode().value();
		List<Banka> banke = response.getBody();
		
	assertEquals(200, statusCode);
	assertNotNull(banke.get(0));
	
	for(Banka b : banke) {
		assertTrue(b.getNaziv().startsWith(naziv));
	}
	}
	
	@Test
	@Order(4)
	void testCreateBanka() {
		Banka banka = new Banka();
		banka.setNaziv("POST NAZIV");
		banka.setKontakt("065226182");
		banka.setPIB(892172);
		
		HttpEntity<Banka> entity = new HttpEntity<Banka>(banka);
		createHighestId();
		ResponseEntity<Banka> response = template
				.exchange("/banka", HttpMethod.POST, entity, Banka.class);
		int statusCode = response.getStatusCode().value();
		Banka createdBanka = response.getBody();
		
		assertEquals(201, statusCode);
		assertEquals(highestId, createdBanka.getId());
		assertEquals(banka.getNaziv(), response.getBody().getNaziv());
		assertEquals(banka.getKontakt(), response.getBody().getKontakt());
		assertEquals(banka.getPIB(), response.getBody().getPIB());
		assertEquals("/banka/" + highestId, response.getHeaders().getLocation().getPath());
	
	}
	
	
	@Test
	@Order(5)
	void testUpdateBanka() {
		Banka banka = new Banka();
		banka.setNaziv("PUT NAZIV");
		banka.setKontakt("065226182");
		banka.setPIB(892172);
		
		HttpEntity<Banka> entity = new HttpEntity<Banka>(banka);
		getHighestId();
		ResponseEntity<Banka> response = template
				.exchange("/banka/id/" + highestId, HttpMethod.PUT, entity, Banka.class);
		int statusCode = response.getStatusCode().value();
	
		assertEquals(200, statusCode);
		assertTrue(response.getBody() instanceof Banka);
		assertEquals(banka.getNaziv(), response.getBody().getNaziv());
		assertEquals(banka.getKontakt(), response.getBody().getKontakt());
		assertEquals(banka.getPIB(), response.getBody().getPIB());

	
	}
	
	@Test
	@Order(6)
	void testDeleteBankaById() {
		getHighestId();
		ResponseEntity<String> response = template
				.exchange("/banka/id/" + highestId, HttpMethod.DELETE,
						null, String.class);
		int statusCode = response.getStatusCode().value();
		
		assertEquals(200, statusCode);
		assertTrue(response.getBody().contains("has been successfully deleted"));

	}
	
}
