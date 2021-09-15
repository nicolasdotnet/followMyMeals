package net.nicolasdot.meal_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.nicolasdot.meal_service.entity.Reporting;
import net.nicolasdot.meal_service.exceptions.ReportingNotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les reporting des repas.")
@RestController
public class ReportingController {

    @Autowired
    private IReportingService iReportingService;

    @ApiOperation("Récupère un reporting sur les repas des 7 derniers jours pour un utilisateur")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping(value = "/api/user/reportings")
    public ResponseEntity<Reporting> getReportingMeals(@RequestParam(name = "user", defaultValue = "") String userId) throws ReportingNotPossibleException {

        Reporting reporting = iReportingService.reporting(userId);

        return ResponseEntity.ok().body(reporting);
    }

}
