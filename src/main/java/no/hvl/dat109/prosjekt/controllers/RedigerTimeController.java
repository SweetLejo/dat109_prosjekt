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
