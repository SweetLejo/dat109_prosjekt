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

import no.hvl.dat109.prosjekt.entity.Bruker;
import no.hvl.dat109.prosjekt.entity.Prosjekt;
import no.hvl.dat109.prosjekt.repo.ProsjektRepo;
import no.hvl.dat109.prosjekt.service.ProsjektService;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProsjektControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProsjektRepo prosjektRepo;

    @InjectMocks
    private ProsjektService prosjektService;

    @InjectMocks
    private ProsjektController prosjektController;

    private Bruker testProsjektBruker = new Bruker();
    private MockHttpSession session = new MockHttpSession();

    @Test
    void testGetOpprettProsjekt() throws Exception {
        session.setAttribute("bruker",testProsjektBruker);
        mockMvc.perform(get("/opprettprosjekt").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("opprettprosjekt"))
                .andExpect(model().attributeExists("prosjekter"));
    }

    /*
    TODO
    @Test
    void testPostProsjekt() throws Exception {
        Prosjekt mockProsjekt = new Prosjekt();
        mockProsjekt.setProsjekt_id("555555");
        mockProsjekt.setNavn("Mockprosjekt");

        when(prosjektService.finnMedID("555555")).thenReturn(mockProsjekt);

        mockMvc.perform(post("/prosjektopprettet")
                .param("prosjekt_id", mockProsjekt.getProsjekt_id())
                .param("navn",mockProsjekt.getNavn()))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("prosjektopprettet"))
                .andExpect(flash().attributeExists("prosjekt"));

        verify(prosjektService, times(1)).lagre(mockProsjekt);
    }

     */




}
