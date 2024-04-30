package no.hvl.dat109.prosjekt.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.servlet.http.HttpServletRequest;

import no.hvl.dat109.prosjekt.Utils.LoginUtil;
import no.hvl.dat109.prosjekt.Utils.PassordUtil;
import no.hvl.dat109.prosjekt.entity.Bruker;
import no.hvl.dat109.prosjekt.service.BrukerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Testklasse for BrukerController.
 */
@WebMvcTest(controllers = BrukerController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EndrePassordControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BrukerService brukerService;
    @InjectMocks
    private BrukerController brukerController;

    /**
     * Tester at redirect går til "/endrepassord" når brukeren er logget inn.
     * @throws Exception dersom det kommer feilmelding ved testkjøring
     */
    @Test
    public void brukerLoggetInnTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        HttpServletRequest request = new MockHttpServletRequest();
        Bruker testBruker = new Bruker();

        LoginUtil.loggInnBruker(request, testBruker);
        session.setAttribute("bruker", testBruker);

        mockMvc.perform(get("/endrepassord").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("endrepassord"));
    }

    /**
     * Tester at redirect går tilbake til "/login" dersom bruker ikke er logget inn.
     * @throws Exception dersom det kommer feilmelding ved testkjøring
     */
    @Test
    public void brukerIkkeLoggetInnTest() throws Exception {
        mockMvc.perform(get("/endrepassord"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("login"))
                .andExpect(flash().attributeExists("feilmelding"))
                .andExpect(flash().attribute("feilmelding", "Du må være logget inn for å endre passord."));
    }
    
    /**
     * Tester at redirect går til "/endrepassord_suksess" etter endret passord.
     * @throws Exception dersom det kommer feilmelding ved testkjøring
     */
    @Test
    public void endrePassordSuksessTest() throws Exception {
    	MockHttpSession session = new MockHttpSession();
        HttpServletRequest request = new MockHttpServletRequest();
        Bruker testBruker = new Bruker();
        
        testBruker.setSalt(PassordUtil.genererTilfeldigSalt());
        testBruker.setHash(PassordUtil.hashMedSalt("passs", testBruker.getSalt()));

        LoginUtil.loggInnBruker(request, testBruker);
        session.setAttribute("bruker", testBruker);

        mockMvc.perform(post("/endrepassord")
        		.session(session)
        		.param("gammeltpassord", "passs")
        		.param("nyttpassord", "12345")
                .param("gjentattnyttPassord", "12345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("endrepassord_suksess"))
                .andExpect(flash().attributeExists("bruker"));
    }
    
    /**
     * Tester om hash og salt blir endret i bruker ved gyldig input.
     * @throws Exception dersom det kommer feilmelding ved testkjøring
     */
    @Test
    public void passordBlirEndretTest() throws Exception {
    	MockHttpSession session = new MockHttpSession();
        HttpServletRequest request = new MockHttpServletRequest();
        Bruker testBruker = new Bruker();
        
        String tidligerePassord = "passs";
        String tidligereSalt = PassordUtil.genererTilfeldigSalt();
        String tidligereHash = PassordUtil.hashMedSalt(tidligerePassord, tidligereSalt);
        String nyttPassord = "12345";
        
        testBruker.setSalt(tidligereSalt);
        testBruker.setHash(tidligereHash);

        LoginUtil.loggInnBruker(request, testBruker);
        session.setAttribute("bruker", testBruker);
    	
        mockMvc.perform(post("/endrepassord")
        		.session(session)
        		.param("gammeltpassord", tidligerePassord)
        		.param("nyttpassord", nyttPassord)
                .param("gjentattnyttPassord", nyttPassord));
        
        assertFalse(testBruker.getSalt().equals(tidligereSalt));
        assertFalse(testBruker.getHash().equals(tidligereHash));
        assertTrue(PassordUtil.erKorrektPassord(nyttPassord, testBruker.getSalt(), testBruker.getHash()));
    }
    
    /**
     * Tester om hash og salt ikke blir endret i bruker ved ugyldig input.
     * @throws Exception dersom det kommer feilmelding ved testkjøring
     */
    @Test
    public void passordBlirIkkeEndretTest() throws Exception {
    	MockHttpSession session = new MockHttpSession();
        HttpServletRequest request = new MockHttpServletRequest();
        Bruker testBruker = new Bruker();
        
        String tidligerePassord = "passs";
        String tidligereSalt = PassordUtil.genererTilfeldigSalt();
        String tidligereHash = PassordUtil.hashMedSalt(tidligerePassord, tidligereSalt);
        
        testBruker.setSalt(tidligereSalt);
        testBruker.setHash(tidligereHash);

        LoginUtil.loggInnBruker(request, testBruker);

        // Test at det ikke blir endret ved for kort nytt passord
        mockMvc.perform(post("/endrepassord")
        		.session(session)
        		.param("gammeltpassord", tidligerePassord)
        		.param("nyttpassord", "123")
                .param("gjentattnyttPassord", "123"));
        
        assertTrue(testBruker.getSalt().equals(tidligereSalt));
        assertTrue(testBruker.getHash().equals(tidligereHash));
        assertTrue(PassordUtil.erKorrektPassord(tidligerePassord, testBruker.getSalt(), testBruker.getHash()));
        
        testBruker.setSalt(tidligereSalt);
        testBruker.setHash(tidligereHash);
        
        // Test at det ikke blir endret ved feil tidligere passord
        mockMvc.perform(post("/endrepassord")
        		.session(session)
        		.param("gammeltpassord", "peaead")
        		.param("nyttpassord", "12345")
                .param("gjentattnyttPassord", "12345"));
        
        assertTrue(testBruker.getSalt().equals(tidligereSalt));
        assertTrue(testBruker.getHash().equals(tidligereHash));
        assertTrue(PassordUtil.erKorrektPassord(tidligerePassord, testBruker.getSalt(), testBruker.getHash()));
        
        testBruker.setSalt(tidligereSalt);
        testBruker.setHash(tidligereHash);
        
        // Test at det ikke blir endret ved ulikt gjentatt nytt passord
        mockMvc.perform(post("/endrepassord")
        		.session(session)
        		.param("gammeltpassord", tidligerePassord)
        		.param("nyttpassord", "12345")
                .param("gjentattnyttPassord", "67890"));
        
        assertTrue(testBruker.getSalt().equals(tidligereSalt));
        assertTrue(testBruker.getHash().equals(tidligereHash));
        assertTrue(PassordUtil.erKorrektPassord(tidligerePassord, testBruker.getSalt(), testBruker.getHash()));
    }
    
    /**
     * Tester at redirect går til "/endrepassord" etter ikke lykket endring av passord.
     * @throws Exception dersom det kommer feilmelding ved testkjøring
     */
    @Test
    public void endrePassordIkkeSuksessTest() throws Exception {
        MockHttpSession session = new MockHttpSession();
        HttpServletRequest request = new MockHttpServletRequest();
        Bruker testBruker = new Bruker();
        
        testBruker.setSalt(PassordUtil.genererTilfeldigSalt());
        testBruker.setHash(PassordUtil.hashMedSalt("passs", testBruker.getSalt()));

        LoginUtil.loggInnBruker(request, testBruker);
        session.setAttribute("bruker", testBruker);

        mockMvc.perform(post("/endrepassord")
        		.session(session)
        		.param("gammeltpassord", "12345")
        		.param("nyttpassord", "125")
                .param("gjentattnyttPassord", "14345"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("endrepassord"))
                .andExpect(flash().attributeExists("endrePassordFeilmelding"));
    }
}



