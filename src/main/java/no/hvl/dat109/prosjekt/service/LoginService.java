package no.hvl.dat109.prosjekt.service;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import no.hvl.dat109.prosjekt.entity.Bruker;

@Service
public class LoginService {

	private static final int MAX_INTERACTIVE_INTERVAL = 10 * 60;


	public static void loggUtBruker(HttpSession session) {
		session.invalidate();
	}

	public static void loggInnBruker(HttpServletRequest request, Bruker bruker) {
		loggUtBruker(request.getSession());
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(MAX_INTERACTIVE_INTERVAL);
		session.setAttribute("bruker", bruker);
	}

	public static boolean erBrukerInnlogget(HttpSession session) {
		return session != null && session.getAttribute("bruker") != null;
	}

}
