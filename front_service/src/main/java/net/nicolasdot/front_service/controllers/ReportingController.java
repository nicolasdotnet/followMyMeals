package net.nicolasdot.front_service.controllers;

import net.nicolasdot.front_service.services.IReportingService;
import java.net.URISyntaxException;
import java.security.Principal;
import net.nicolasdot.front_service.beans.Reporting;
import net.nicolasdot.front_service.services.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author nicolasdotnet
 */
@Controller
@Transactional
public class ReportingController {

    private final Logger log = LogManager.getLogger(ReportingController.class);

    @Autowired
    private IReportingService iReportingService;

    @GetMapping("/user/reportings")
    public String reportingMeal(Model model, Principal principal) throws URISyntaxException {

        log.debug("reportingMeal()");

        Reporting reporting = null;

        try {
            reporting = iReportingService.reporting(principal.getName());
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "reporting/show";
        }

        model.addAttribute("reporting", reporting);

        return "reporting/show";

    }

}
