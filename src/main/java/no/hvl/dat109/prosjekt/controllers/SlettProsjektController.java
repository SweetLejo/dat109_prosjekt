package no.hvl.dat109.prosjekt.controllers;


import no.hvl.dat109.prosjekt.Utils.LoginUtil;
import no.hvl.dat109.prosjekt.entity.*;
import no.hvl.dat109.prosjekt.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

import java.util.List;

@Controller
public class SlettProsjektController {

    @Autowired
    private ProsjektService prosjektService;

    @Autowired
    private TimeService timeService;

    /**
     * Viser skjemaet for sletting av prosjekt.
     * Metoden sjekker først at brukeren er innlogget ved sjekk av HttpSession.
     * Hvis brukeren ikke er logget inn, blir brukeren omdirigert til innloggingen med feilmelding.
     *
     * @param session   Den nåværende {@link HttpSession} som brukes for å verifisere om brukeren er innlogget
     * @param model     {@link Model} objekt som brukes for å sende data til visningen
     * @param ra        {@link RedirectAttributes} som brukes for å sende feilmeldinger over til redirect
     * @return          {@link String} som representerer navnet på visningen som skal hentes.
     */
    @GetMapping("slettprosjekt")
    public String getSlettProsjekt(HttpSession session, Model model, RedirectAttributes ra) {
        if(!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å slette prosjekt");
            return "redirect:login";
        }

        model.addAttribute("prosjekter", prosjektService.finnAlle());

        return "slettprosjekt";
    }

    /**
     * Behandler data fra skjemaet for å slette et {@link Prosjekt}.
     * Prosjekt-objektet, hentet med inntastet prosjektid sletter objectet ved hjelp av {@link ProsjektService}
     *
     * @param prosjekt_id   Den unike identifikatoren for prosjektet som skal slettes
     * @param ra            {@link RedirectAttributes} som brukes for omdirigeringsattributter
     * @return              En {@link String} som representerer en omdirigering til neste visning, eller laster skjema på nytt
     */
    @PostMapping("slettprosjekt")
    public String postSlettProsjekt(@RequestParam String prosjekt_id, RedirectAttributes ra) {
        if(prosjekt_id == null) {
            ra.addFlashAttribute("feilmeldinger", "ID kan ikke være tomt");
            return "redirect:slettprosjekt";
        }
        if (!prosjekt_id.matches("\\d+")) {
            ra.addFlashAttribute("feilmeldinger", "ID må være numerisk");
            return "redirect:slettprosjekt";
        }
        if(prosjekt_id.length() != 6) {
            ra.addFlashAttribute("feilmeldinger", "ID må være 6 siffer");
            return "redirect:slettprosjekt";
        }
        if(!prosjektService.finnMedID(prosjekt_id).isPresent()) {
            ra.addFlashAttribute("feilmeldinger", "finner ikke id");
            return "redirect:slettprosjekt";
        }

        Prosjekt slettProsjekt = prosjektService.finnMedID(prosjekt_id).get();
        prosjektService.slettProsjekt(slettProsjekt);

        ra.addFlashAttribute("prosjekt", slettProsjekt);

        return "redirect:slettprosjekt_suksess";

    }

    /**
     * Viser en bekreftelse på at prosjektet er slettet.
     * Denne metoden forutsetter at nødvendig informasjon om det nylig opprettede prosjektet er
     * lagt til i modellen via RedirectAttributes i metoden som håndterer POST-forespørselen for prosjektopprettelsen.
     *
     * @param model Model-objektet som brukes for å sende data om det slettede prosjektet til visningen.
     * @return En {@link String} som representerer navnet på visningen som skal hentes
     */
    @GetMapping("slettprosjekt_suksess")
    public String getSlettProsjekt_suksess(Model model) {
        return "slettprosjekt_suksess";
    }

}
