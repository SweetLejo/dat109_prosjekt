package no.hvl.dat109.prosjekt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import no.hvl.dat109.prosjekt.repo.ProsjektRepo;
import no.hvl.dat109.prosjekt.service.ProsjektService;
import no.hvl.dat109.prosjekt.entity.Prosjekt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class ProsjektServiceTest {

	private ProsjektService prosjektService;
	private ProsjektRepo prosjektRepo;

	@BeforeEach
	public void setup() {
		prosjektRepo = mock(ProsjektRepo.class);
		prosjektService = new ProsjektService();
	}

	@Test
	public void finnAlleTest() {
		List<Prosjekt> prosjekter = new ArrayList<>();
		Prosjekt prosjekt1 = new Prosjekt();
		prosjekt1.setId("110000");
		prosjekt1.setNavn("Adm");
		prosjekter.add(prosjekt1);

		Prosjekt prosjekt2 = new Prosjekt();
		prosjekt2.setId("120000");
		prosjekt2.setNavn("Salg");
		prosjekter.add(prosjekt2);

		ProsjektService mockProsjektService = mock(ProsjektService.class);
		when(mockProsjektService.finnAlle()).thenReturn(prosjekter);

		List<Prosjekt> testResultatet = mockProsjektService.finnAlle();
		assertEquals(prosjekter, testResultatet);
		assertEquals(2, testResultatet.size());
	}

	//TODO - Testene jobbes med
	/*
	@Test
	public void finnMedIdTest() {

		Prosjekt prosjekt = new Prosjekt();
		prosjekt.setId("115000");
		prosjekt.setNavn("HR");
		System.out.println(prosjekt.getId());

		when(prosjektRepo.findById(115000)).thenReturn(Optional.of(prosjekt));

		Prosjekt testResultat = prosjektService.finnMedID("115000");
		assertEquals(prosjekt, testResultat);
		assertEquals("115000", testResultat.getId());
		assertEquals("HR", testResultat.getNavn());


	}

	@Test
	public void testLagre() {
		Prosjekt prosjekt = new Prosjekt();
		prosjekt.setNavn("Testprosjekt");

		when(prosjektRepo.save(prosjekt)).thenReturn(prosjekt);
		Prosjekt lagretProsjekt = prosjektService.lagre(prosjekt);

		assertNotNull(lagretProsjekt.getId());
		assertEquals("Testprosjekt", lagretProsjekt.getNavn());
	}

	 */

}
