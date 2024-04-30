package no.hvl.dat109.prosjekt.controllers;

import no.hvl.dat109.prosjekt.Utils.LoginUtil;
import no.hvl.dat109.prosjekt.entity.Bruker;
import no.hvl.dat109.prosjekt.entity.Prosjekt;
import no.hvl.dat109.prosjekt.entity.Time;
import no.hvl.dat109.prosjekt.service.ProsjektService;
import no.hvl.dat109.prosjekt.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Controller for behandling av timer i timeføringsystemet.
 */
@Controller
public class TimeController {

    @Autowired
    private TimeService timeService;
    @Autowired
    ProsjektService prosjektService;
    ;

    /**
     * GET-forespørsel som henter viewet for registrering av en ny time.
     *
     * @param session inneholder brukerobjektet som brukes for å sjekke innlogging.
     * @param model
     * @param ra      {@link RedirectAttributes} som brukes for å sende feilmeldinger over til redirect
     * @return
     */
    @GetMapping("registrertime")
    public String getRegistrerTime(
            HttpSession session, Model model, RedirectAttributes ra) {

        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å registrere timer");
            return "redirect:login";
        }
        model.addAttribute("prosjekter", prosjektService.finnAlle());
        return "registrertime";
    }

    /**
     * Registrerer en ny time og lagrer den i databasen.
     *
     * @param session inneholder brukerobjektet som brukes for å sjekke innlogging.
     * @param model
     * @param ra      {@link RedirectAttributes} som brukes for å sende feilmeldinger over til redirect.
     * @return
     */
    @PostMapping("registrertime")
    public String registrerTimer(
            HttpSession session, Model model, RedirectAttributes ra,
            @RequestParam String prosjekt_id, @RequestParam int antallTimer) {
        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å registrere timer");
            return "redirect:login";
        }

        Optional<Prosjekt> optionalProsjekt = prosjektService.finnMedID(prosjekt_id);
        Prosjekt prosjekt = optionalProsjekt.orElse(null);

        if (prosjekt == null) {
            ra.addFlashAttribute("feilmelding", "Tast inn et prosjekt fra prosjektoversikten");
            return "redirect:registrertime";
        }
        Bruker bruker = (Bruker) session.getAttribute("bruker");
        Time time = new Time(antallTimer, bruker, prosjekt);

        timeService.lagreTime(time);

        ra.addFlashAttribute("time", time);
        ra.addFlashAttribute("prosjekt", prosjekt);

        return "redirect:registrertime";
    }
}
