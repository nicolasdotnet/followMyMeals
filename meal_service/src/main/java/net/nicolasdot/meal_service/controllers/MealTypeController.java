package net.nicolasdot.meal_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import net.nicolasdot.meal_service.dto.MealTypeDTO;
import net.nicolasdot.meal_service.entity.MealType;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;
import net.nicolasdot.meal_service.services.interfaces.IMealTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author nicolasdotnet
 */
@Api(tags = "API pour les opérations CRUD sur les type de repas.")
@RestController
public class MealTypeController {

    @Autowired
    private IMealTypeService iMealTypeService;
    
    private static final Logger log = LogManager.getLogger(MealTypeController.class);

    @ApiOperation("Enregister un nouveau type de repas")
    @PostMapping("/api/user/types")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "type de repas créé", response = MealType.class),
        @ApiResponse(code = 400, message = "erreur de saisie", response = MealType.class),
        @ApiResponse(code = 409, message = "la categorie existe déjà dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    public ResponseEntity saveMealType(@Valid @RequestBody MealTypeDTO mealDto) throws EntityAlreadyExistsException {

        log.debug("saveMealType()");
        MealType mealTypeSave = iMealTypeService.register(mealDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(mealTypeSave.getId())
                .toUri();

        return ResponseEntity.created(location)
                .body(mealTypeSave);

    }

    @ApiOperation("Récupère l'emsemble des type de repas")
    @ApiResponses(value = {
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/types")
    public ResponseEntity showMealTypes() {

        log.debug("showMealTypes()");
        List<MealType> mealTypesFind = iMealTypeService.getAllMealTypes();

        return ResponseEntity.ok(mealTypesFind);

    }

}
