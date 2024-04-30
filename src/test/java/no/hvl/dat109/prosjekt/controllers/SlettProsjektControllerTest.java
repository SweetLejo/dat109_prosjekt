package no.hvl.dat109.prosjekt.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import no.hvl.dat109.prosjekt.entity.Bruker;
import no.hvl.dat109.prosjekt.entity.Prosjekt;
import no.hvl.dat109.prosjekt.repo.ProsjektRepo;
import no.hvl.dat109.prosjekt.service.ProsjektService;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SlettProsjektControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProsjektRepo prosjektRepo;

    @InjectMocks
    private ProsjektService prosjektService;

    @InjectMocks
    private SlettProsjektController slettProsjektController;

    private Bruker testProsjektBruker = new Bruker();
    private MockHttpSession session = new MockHttpSession();
    private static final String MOCK_PROSJEKT_ID = "555555";

    @Test
    void testGetSlettProsjekt() throws Exception{
        session.setAttribute("bruker",testProsjektBruker);
        mockMvc.perform(get("/slettprosjekt").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("slettprosjekt"))
                .andExpect(model().attributeExists("prosjekter"));
    }

    @Test
    void testPostSlettProsjekt() throws Exception {
        Prosjekt prosjekt = new Prosjekt();
        prosjekt.setProsjekt_id(MOCK_PROSJEKT_ID);

        when(prosjektService.finnMedID(MOCK_PROSJEKT_ID)).thenReturn(Optional.of(prosjekt));

        mockMvc.perform(post("/slettprosjekt")
                        .param("prosjekt_id", MOCK_PROSJEKT_ID))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("slettprosjekt_suksess"))
                .andExpect(flash().attributeExists("prosjekt"));

        /* TODO - Implementere testlogikk for sletting av prosjektet
        Optional<Prosjekt> deletedProsjekt = prosjektService.finnMedID(MOCK_PROSJEKT_ID);
        Assertions.assertFalse(deletedProsjekt.isPresent());
         */

    }

    @Test
    public void testPostSlettProsjekt_InvalidInput_RedirectWithError() throws Exception {
        String invalidProsjektId = "ABC"; // Invalid ID

        mockMvc.perform(post("/slettprosjekt")
                        .param("prosjekt_id", invalidProsjektId))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("slettprosjekt"))
                .andExpect(flash().attributeExists("feilmeldinger"));
    }

    @Test
    public void testPostSlettProsjekt_NonexistentProject_RedirectWithError() throws Exception {
        String nonexistentProsjektId = "999999"; // Nonexistent ID

        mockMvc.perform(post("/slettprosjekt")
                        .param("prosjekt_id", nonexistentProsjektId))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("slettprosjekt"))
                .andExpect(flash().attributeExists("feilmeldinger"));
    }

}
