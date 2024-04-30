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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class RedigerTimeController {

    @Autowired
    TimeService timeService;

    @Autowired
    ProsjektService prosjektService;

    /**
     * Viser skjemaet for redigering av en time.
     * Metoden sjekker først at brukeren er innlogget ved sjekk av HttpSession.
     * Hvis brukeren ikke er logget inn, blir brukeren omdirigert til innloggingen med feilmelding.
     *
     * @param session Den nåværende {@link HttpSession} som brukes for å verifisere om brukeren er innlogget
     * @param model   {@link Model} objekt som brukes for å sende data til visningen
     * @param ra      {@link RedirectAttributes} som brukes for å sende feilmeldinger over til redirect
     * @return {@link String} som representerer navnet på visningen som skal hentes.
     */
    @GetMapping("redigertimer")
    public String getRedigerTimer(HttpSession session, Model model, RedirectAttributes ra) {

        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å registrere timer");
            return "redirect:login";
        }
        Bruker bruker = (Bruker) session.getAttribute("bruker");
        model.addAttribute("prosjekter", prosjektService.finnAlle());
        model.addAttribute("timeliste", timeService.finnAlleTimer());

        return "redigertime";
    }

    /**
     * Velger et timeobjekt spesifisert i POST-forespørselen, og lagrer det i session.
     *
     * @param session Lagrer det angitte time objektet
     * @param ra
     * @param time_id ID-en til et timeobjekt
     * @return
     */
    @PostMapping("velgtime")
    public String postVelgProsjekt(HttpSession session, RedirectAttributes ra,
                                   @RequestParam String time_id) {

        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å redigere timer");
            return "redirect:login";
        }
        Bruker bruker = (Bruker) session.getAttribute("bruker");

        if (timeService.finnMedId(Integer.valueOf(time_id)) == null) {
            ra.addFlashAttribute("feilmelding", "Tast inn en time fra prosjektoversikten");
            return "redirect:redigertimer";
        }
        Time time = timeService.finnMedId(Integer.valueOf(time_id));
        session.setAttribute("time", time);
        return "redirect:redigertimer";
    }

    /**
     * Behandler en POST-forespørsel for å redigere en time med den angitte time-ID-en.
     * Etter vellykket redigering av time, blir en suksessmelding lagt til og brukeren blir omdirigert til redigeringssiden.
     *
     * @param session Den angitte timeobjektet som skal redigeres
     * @param antallTimer Nytt antall timer i et time objekt
     * @param ra RedirectAttributes for å legge til feilmeldinger og suksessmeldinger for viderekobling
     * @return En String som representerer URL-en for å omdirigere brukeren etter behandling av forespørselen
     */
    @PostMapping("redigertime")
    public String postRedigerTime(HttpSession session, RedirectAttributes ra,
                                  @RequestParam int antallTimer) {

        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å redigere timer");
            return "redirect:login";
        }
        Time time = (Time) session.getAttribute("time");
        time.setAntallTimer(antallTimer);
        timeService.lagreTime(time);
        session.removeAttribute("time");

        return "redirect:redigertimer";
    }

    /**
     * Behandler data fra skjemaet for å slette en {@link Time}.
     * Time-objektet, hentet med inntastet time_id sletter objectet ved hjelp av {@link TimeService}
     *
     * @param session       Den unike identifikatoren for timen som skal slettes
     * @param ra            {@link RedirectAttributes} som brukes for omdirigeringsattributter
     * @return              En {@link String} som representerer en omdirigering til neste visning, eller laster skjema på nytt
     */
    @PostMapping("sletttime")
    public String postSlettTime(HttpSession session, RedirectAttributes ra) {

        if (!LoginUtil.erBrukerInnlogget(session)) {
            ra.addFlashAttribute("feilmelding", "Du må være innlogget for å redigere timer");
            return "redirect:login";
        }
        Time time = (Time) session.getAttribute("time");
        timeService.slettTime(time.getTime_id());
        session.removeAttribute("time");

        return "redirect:redigertimer";
    }
}
