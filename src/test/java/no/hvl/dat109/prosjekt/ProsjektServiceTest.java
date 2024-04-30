package no.hvl.dat109.prosjekt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import no.hvl.dat109.prosjekt.repo.ProsjektRepo;
import no.hvl.dat109.prosjekt.service.ProsjektService;
import no.hvl.dat109.prosjekt.entity.Prosjekt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProsjektServiceTest {

	@Autowired
	private ProsjektService prosjektService;

	@MockBean
	private ProsjektRepo prosjektRepo;

	/**
	 * Test av {@link ProsjektService#finnAlle()}.
	 * To nye prosjekter opprettes og legges til.
	 * Metodene linker til en ny liste og tester sammenligning og størrelse
	 */
	@Test
	public void finnAlleTest() {
		List<Prosjekt> prosjekter = new ArrayList<>();
		Prosjekt prosjekt1 = new Prosjekt();
		prosjekt1.setProsjekt_id("110000");
		prosjekt1.setNavn("Adm");
		prosjekter.add(prosjekt1);

		Prosjekt prosjekt2 = new Prosjekt();
		prosjekt2.setProsjekt_id("120000");
		prosjekt2.setNavn("Salg");
		prosjekter.add(prosjekt2);

		when(prosjektRepo.findAll()).thenReturn(prosjekter);

		List<Prosjekt> testResultatet = prosjektService.finnAlle();
		assertEquals(prosjekter, testResultatet);
		assertEquals(2, testResultatet.size());
	}

	/**
	 * Test av {@link ProsjektService#finnMedID(String)}.
	 * Legger til ett prosjekt og søker etter id
	 */
	@Test
	public void finnMedIdTest() {

		Prosjekt prosjekt = new Prosjekt();
		prosjekt.setProsjekt_id("115000");
		prosjekt.setNavn("HR");

		when(prosjektRepo.findById("115000")).thenReturn(Optional.of(prosjekt));

		Optional<Prosjekt> optionalProsjekt = prosjektService.finnMedID("115000");
		Prosjekt testResultat = optionalProsjekt.orElse(null);
		assertEquals(prosjekt, testResultat);
		assertEquals("115000", testResultat.getProsjekt_id());
		assertEquals("HR", testResultat.getNavn());
	}

	/**
	 * Test av {@link ProsjektService#lagre(Prosjekt)}.
	 * Legger til ett prosjekt og tester at det blir lagt til.
	 */
	@Test
	public void testLagre() {
		Prosjekt prosjekt = new Prosjekt();
		prosjekt.setNavn("Testprosjekt");
		prosjekt.setProsjekt_id("303030");

		when(prosjektRepo.save(prosjekt)).thenReturn(prosjekt);
		Prosjekt lagretProsjekt = prosjektService.lagre(prosjekt);

		assertNotNull(lagretProsjekt.getProsjekt_id());
		assertEquals("Testprosjekt", lagretProsjekt.getNavn());
	}

	/**
	 * Test av {@link ProsjektService#slettProsjekt(Prosjekt)}.
	 * Legger til et prosjekt og tester at det blir slettet.
	 */
	@Test
	public void testSlett() {
		Prosjekt prosjekt = new Prosjekt();
		prosjekt.setProsjekt_id("232323");
		prosjekt.setNavn("testslett");

		prosjektService.slettProsjekt(prosjekt);

		verify(prosjektRepo).delete(prosjekt);
	}

	/**
	 * Test av {@link ProsjektService#redigerProsjekt(String, String)}.
	 * Legger til et prosjekt med id og navn. Forsøker så å teste endring.
	 */
	@Test
	public void testRedigerProsjekt() {
		String gammelProsjektId = "123456";
		String nyttProsjektnavn = "Nytt prosjektnavn";

		Prosjekt prosjekt = new Prosjekt();
		prosjekt.setProsjekt_id(gammelProsjektId);
		prosjekt.setNavn("Gammelt prosjektnavn");
		Optional<Prosjekt> maybeProsjekt = Optional.of(prosjekt);

		when(prosjektRepo.findById(gammelProsjektId)).thenReturn(maybeProsjekt);

		prosjektService.redigerProsjekt(gammelProsjektId, nyttProsjektnavn);

		verify(prosjektRepo).save(prosjekt);

		assertEquals(nyttProsjektnavn, prosjekt.getNavn());
	}



}
