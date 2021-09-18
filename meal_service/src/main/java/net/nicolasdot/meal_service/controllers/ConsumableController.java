package net.nicolasdot.meal_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import net.nicolasdot.meal_service.dto.ConsumableDTO;
import net.nicolasdot.meal_service.entity.Consumable;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.NotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IConsumableService;
import net.nicolasdot.meal_service.specifications.ConsumableCriteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les opérations CRUD sur les consommations.")
@RestController
public class ConsumableController {

    @Autowired
    private IConsumableService iConsumableService;
    
    private static final Logger log = LogManager.getLogger(ConsumableController.class);

    @ApiOperation("Enregister une consommation")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "consommation créée", response = Consumable.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = Consumable.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PostMapping("/api/user/consumables")
    public ResponseEntity<Consumable> saveConsumables(@Valid @RequestBody ConsumableDTO consumableDTO) throws EntityNotFoundException, NotPossibleException {

        log.debug("saveConsumables()");
        Consumable consumable = iConsumableService.addConsumable(consumableDTO);

        return ResponseEntity.ok().body(consumable);

    }

    @ApiOperation("Consulter les informations sur une consommation")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Consumable.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 404, message = "le produit n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/consumables/{id}")
    public ResponseEntity<Consumable> getConsumable(@PathVariable("id") int id) throws EntityNotFoundException {

        log.debug("getConsumable()");
        Consumable consumable = iConsumableService.getConsumableById(Long.valueOf(id));

        return ResponseEntity.ok(consumable);

    }

    @ApiOperation("Récupère l'ensemble des consommables en fonction de son numero, du repas id et du user id")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping(value = "/api/user/consumables")
    public ResponseEntity<Page<Consumable>> showAllconsumablesByCriteria(
            @RequestParam(name = "id", defaultValue = "") String consumableId,
            @RequestParam(name = "meal", defaultValue = "") String mealId,
            @RequestParam(name = "user", defaultValue = "") String userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {

        log.debug("showAllconsumablesByCriteria()");
        ConsumableCriteria consumableCriteria = new ConsumableCriteria();
        consumableCriteria.setConsumableId("".equals(consumableId) ? null : Long.parseLong(consumableId));
        consumableCriteria.setMealId("".equals(mealId) ? null : Long.parseLong(mealId));
        consumableCriteria.setUserId("".equals(userId) ? null : userId);

        Page<Consumable> pageMeal = iConsumableService.getAllConsumablesByCriteria(consumableCriteria, page, size);

        return ResponseEntity.ok().body(pageMeal);
    }

    @ApiOperation("Supprimer une consommation")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Consumable.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @DeleteMapping("/api/user/consumables/{id}")
    void deleteProduct(@PathVariable("id") int id) throws EntityNotFoundException, NotPossibleException {

        log.debug("deleteProduct()");
        iConsumableService.removeConsumable(Long.valueOf(id));

    }

}
