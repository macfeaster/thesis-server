package se.kth.mauritzz.thesis.controllers;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import se.kth.mauritzz.thesis.models.entities.User;
import se.kth.mauritzz.thesis.models.repositories.UserRepository;
import se.kth.mauritzz.thesis.tinkapi.unauthenticated.TinkUnauthenticatedApi;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@AllArgsConstructor
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final TinkUnauthenticatedApi apiUnauthenticated;
    private final UserRepository userRepository;
    private final AuthenticationManager authManager;

    @RequestMapping("/")
    public String index() {
        return "redirect:/manage";
    }

    @GetMapping("/login")
    public String login(@CookieValue(value = "uid", defaultValue = "") String uid, Model model) {
        model.addAttribute("uid", uid);
        return "auth/login";
    }

    @PostMapping("/create")
    public Object doCreate(HttpServletRequest request, HttpServletResponse response) {
        final var user = apiUnauthenticated.createUser();
        // final var token = apiUnauthenticated.authenticateUser(user);
        userRepository.save(user);

        // TinkApi api = new TinkApi(token.getAccessToken());
        // final var tinkUser = api.getUser();
        // tinkUser.getFlags().add("BYPASS_PROVIDER_FILTERING");
        // logger.info("{}", api.updateUser(tinkUser));

        var authReq = new UsernamePasswordAuthenticationToken(user, User.PASSWORD);
        var auth = authManager.authenticate(authReq);

        var sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        var session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);

        var cookie = new Cookie("uid", user.getId());
        response.addCookie(cookie);

        return "redirect:/manage";
    }

}
