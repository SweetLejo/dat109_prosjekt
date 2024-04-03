package no.hvl.dat109.prosjekt.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import no.hvl.dat109.prosjekt.service.BrukerService;
import no.hvl.dat109.prosjekt.service.LoginService;
import no.hvl.dat109.prosjekt.service.PassordService;
import no.hvl.dat109.prosjekt.service.SystemService;
import no.hvl.dat109.prosjekt.entity.Bruker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OpprettbrukerController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private BrukerService brukerService;
    @Autowired
    PassordService passordService;

    @PostMapping("paamelding")
    public String postPaamelding(
            @Valid @ModelAttribute("ansatt") Bruker bruker, RedirectAttributes ra,
            BindingResult bindingResult, @RequestParam String passord,
            HttpServletRequest request) {
        ra.addFlashAttribute("ansatt", bruker);
        if (bindingResult.hasErrors()) {
            String feilmeldinger = bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (f, e) -> f + e + "<br>");
            ra.addFlashAttribute("feilmeldinger", feilmeldinger);
            return "redirect:paamelding";
        }
        if (brukerService.finnBrukerMedMobil(bruker.getMobil()) != null) {
            ra.addFlashAttribute("feilmeldinger", "Mobilnummeret er allerede registrert");
            return "redirect:paamelding";
        }
        if (passord.length() < 5) {
            ra.addFlashAttribute("feilmeldinger", "Passordet må være minst 5 tegn");
            return "redirect:paamelding";
        }
        String salt = passordService.genererTilfeldigSalt();
        String hash = passordService.hashMedSalt(passord, salt);
        bruker.setSalt(salt);
        bruker.setHash(hash);
        loginService.loggInnBruker(request, bruker);
        brukerService.lagreBruker(bruker);
        return "redirect:paameldt";
    }

    @GetMapping("paamelding")
    public String getPaamelding(Model model) {
        return "paamelding";
    }

    @GetMapping("paameldt")
    public String getPaameldt(Model model) {
        return "paameldt";
    }

}
