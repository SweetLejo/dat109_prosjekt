package no.hvl.dat109.prosjekt.controllers;

import no.hvl.dat109.prosjekt.Utils.LoginUtil;
import no.hvl.dat109.prosjekt.entity.Prosjekt;
import no.hvl.dat109.prosjekt.service.ProsjektService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
public class redigerProsjektController {

    @Autowired
    private ProsjektService prosjektService;

    /**
     * Viser skjemaet for redigering av prosjekt.
     * Metoden sjekker først at brukeren er innlogget ved sjekk av HttpSession.
     * Hvis brukeren ikke er logget inn, blir brukeren omdirigert til innloggingen med feilmelding.
     *
     * @param session Den nåværende {@link HttpSession} som brukes for å verifisere om brukeren er innlogget
     * @param model   {@link Model} objekt som brukes for å sende data til visningen
     * @param ra      {@link RedirectAttributes} som brukes for å sende feilmeldinger over til redirect
     * @return {@link String} som representerer navnet på visningen som skal hentes.
     */
    @GetMapping("redigerProsjekt")
    public String getRedigerProsjekt(HttpSession session, Model model, RedirectAttributes ra) {
        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å redigere prosjekt");
            return "redirect:login";
        }

        model.addAttribute("prosjekter", prosjektService.finnAlle());

        //Logikk her
        return "redigerProsjekt";
    }

    /**
     * Behandler en POST-forespørsel for å redigere et prosjekt med den angitte prosjekt-ID-en og navnet.
     * Hvis enten prosjekt-ID eller navn er tomme, blir en feilmelding lagt til og brukeren blir omdirigert til redigeringssiden.
     * Hvis prosjekt-ID-en ikke er numerisk eller ikke finnes i databasen, blir det lagt til en feilmelding og brukeren blir omdirigert til redigeringssiden.
     * Hvis prosjekt-ID-en ikke består av 6 siffer, blir det lagt til en feilmelding og brukeren blir omdirigert til redigeringssiden.
     * Etter vellykket redigering av prosjektnavnet, blir en suksessmelding lagt til og brukeren blir omdirigert til redigeringssiden.
     *
     * @param prosjekt_id Den angitte prosjekt-ID-en som skal redigeres
     * @param prosjektnavn Det nye navnet som prosjektet skal oppdateres med
     * @param ra RedirectAttributes for å legge til feilmeldinger og suksessmeldinger for viderekobling
     * @return En String som representerer URL-en for å omdirigere brukeren etter behandling av forespørselen
     */
    @PostMapping("redigerProsjekt")
    public String postRedigerProsjekt(@RequestParam String prosjekt_id, @RequestParam String prosjektnavn, RedirectAttributes ra) {
        if (prosjekt_id == null || prosjektnavn == null) {
            ra.addFlashAttribute("feilmeldinger", "ID eller navn kan ikke være tomme");
            return "redirect:redigerProsjekt";
        }
        if (!prosjekt_id.matches("\\d+")) {
            ra.addFlashAttribute("feilmeldinger", "ID må være numerisk");
            return "redirect:redigerProsjekt";
        }
        if (!prosjektService.finnMedID(prosjekt_id).isPresent()) {
            ra.addFlashAttribute("feilmeldinger", "Finner ikke prosjektet");
            return "redirect:redigerProsjekt";
        }
        if (prosjekt_id.length() != 6) {
            ra.addFlashAttribute("feilmeldinger", "ID må være 6 siffer");
            return "redirect:redigerProsjekt";
        }
        if(prosjektnavn.isEmpty()) {
            ra.addFlashAttribute("feilmeldinger","Må skrive inn navn");
            return "redirect:redigerProsjekt";
        }
        prosjektService.redigerProsjekt(prosjekt_id, prosjektnavn);
        ra.addFlashAttribute("suksessmelding", "Navnet til " + prosjekt_id + " ble oppdatert til " + prosjektnavn);
        return "redirect:redigerProsjekt";
    }

}




