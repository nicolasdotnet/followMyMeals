package net.nicolasdot.meal_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import javax.validation.Valid;
import net.nicolasdot.meal_service.dto.MealDTO;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.exceptions.EntityNotFoundException;
import net.nicolasdot.meal_service.exceptions.MealNotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IMealService;
import net.nicolasdot.meal_service.specifications.MealCriteria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les opérations CRUD sur les repas.")
@RestController
public class MealController {

    @Autowired
    private IMealService iMealService;
    
    private static final Logger log = LogManager.getLogger(MealController.class);

    @ApiOperation("Enregister un nouveau repas")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "repas créé", response = Meal.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = Meal.class),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PostMapping("/api/user/meals")
    public ResponseEntity<Meal> saveMeal(@Valid @RequestBody MealDTO mealDto) throws EntityNotFoundException {

        log.debug("saveMeal()");
        Meal mealSave = iMealService.createMeal(mealDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mealSave.getId())
                .toUri();

        return ResponseEntity.created(location).body(mealSave);

    }

    @ApiOperation("Récupère une repas grâce à son ID !")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Meal.class),
        @ApiResponse(code = 404, message = "le repas n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/meals/{id}")
    public ResponseEntity showMeal(@PathVariable(value = "id") int id) throws EntityNotFoundException {

        log.debug("showMeal() id: {}", id);
        Meal meal = iMealService.getOneMeal(Long.valueOf(id));

        return ResponseEntity.ok(meal);

    }

    @ApiOperation("Enregistrer une validation de repas.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "valider un repas", response = Meal.class),
        @ApiResponse(code = 404, message = "la réservation n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/meals/{id}/validate")
    public ResponseEntity validateMeal(@PathVariable("id") int id) throws EntityNotFoundException, MealNotPossibleException {

        log.debug("validateMeal() id: {}", id);
        Meal meal = iMealService.validateMeal(Long.valueOf(id));

        return ResponseEntity.ok(meal);

    }

    @ApiOperation("Récupère l'ensemble des repas en fonction de son numero, de l'auteur et du numero ISBN")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping(value = "/api/user/meals")
    public ResponseEntity<Page<Meal>> showAllMealsByCriteria(
            @RequestParam(name = "id", defaultValue = "") String mealId,
            @RequestParam(name = "type", defaultValue = "") String mealType,
            @RequestParam(name = "user", defaultValue = "") String userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size) {

        log.debug("showAllMealsByCriteria()");
        MealCriteria mealCriteria = new MealCriteria();
        mealCriteria.setMealId(Long.getLong(userId));
        mealCriteria.setMealtype(mealType);
        mealCriteria.setUserId(userId);

        Page<Meal> pageMeal = iMealService.getAllMealsByCriteria(mealCriteria, page, size);

        return ResponseEntity.ok().body(pageMeal);
    }

    @ApiOperation("Supprimer un repas")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Meal.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 404, message = "le repas n'existe pas"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @DeleteMapping("/api/user/meals/{id}")
    void deleteMeal(@PathVariable("id") int id) throws EntityNotFoundException {

        log.debug("deleteMeal() id: {}", id);
        iMealService.deleteMeal(Long.valueOf(id));

    }

}
