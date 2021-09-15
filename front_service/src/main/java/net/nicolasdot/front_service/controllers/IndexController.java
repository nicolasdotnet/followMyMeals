package net.nicolasdot.front_service.controllers;

import java.security.Principal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.spi.HttpFacade;
import org.keycloak.adapters.springsecurity.facade.SimpleHttpFacade;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author nicolasdotnet
 */
@Controller
public class IndexController {

    private final Logger log = LogManager.getLogger(IndexController.class);

    @Autowired
    private AdapterDeploymentContext adapterDeploymentContext;

    // get default page
    @GetMapping("/")
    public String index(Model model, Principal principal) {
        log.debug("index()");

        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;

        if (keycloakAuthenticationToken != null) {

            AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
            model.addAttribute("username", accessToken.getGivenName());

        }

        return "index";
    }

    // get confirmation page
    @GetMapping("/confirmation")
    public String confirmation() {
        return "common/infos";

    }

    // get information page
    @GetMapping("/infos")
    public String information() {
        return "common/infos";

    }

    // show 403 page
    @GetMapping("/403")
    public String accessDenied() {

        log.debug("accessDenied()");

        return "common/403";

    }

    // get logout 
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        log.debug("logout()");

        request.logout();
        return "redirect:/";
    }

    // get change password page 
    @GetMapping("/change-password")
    public String changePassword(RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse reponse) throws ServletException {
        log.debug("changePassword()");

        HttpFacade facade = new SimpleHttpFacade(request, reponse);

        KeycloakDeployment deployment = adapterDeploymentContext.resolveDeployment(facade);
        redirect.addAttribute("referrer", deployment.getResourceName());

        return "redirect:" + deployment.getAccountUrl() + "/#/security/signingin";
    }

}
