package net.nicolasdot.front_service.controllers;

import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.nicolasdot.front_service.beans.Consumable;
import net.nicolasdot.front_service.beans.Meal;
import net.nicolasdot.front_service.beans.Reporting;
import net.nicolasdot.front_service.dto.ConsumableDTO;
import net.nicolasdot.front_service.dto.MealDTO;
import net.nicolasdot.front_service.services.IConsumableService;
import net.nicolasdot.front_service.services.IMealService;
import net.nicolasdot.front_service.services.IMealTypeService;
import net.nicolasdot.front_service.services.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author nicolasdotnet
 */
@Controller
@Transactional
public class MealController {

    private final Logger log = LogManager.getLogger(MealController.class);

    @Autowired
    private IMealService iMealService;

    @Autowired
    private IConsumableService iConsumableService;

    @Autowired
    private IMealTypeService iMealTypeService;

    @GetMapping("/user/meal/add")
    public String showAddMealForm(Model model) throws URISyntaxException {

        log.debug("showAddMealForm()");
        model.addAttribute("mealDTO", new MealDTO());
        model.addAttribute("listMealType", iMealTypeService.getAllMealTypes());

        return "/meal/addform";

    }

    @PostMapping("/user/meal")
    public String saveMeal(@ModelAttribute MealDTO mealDTO, Model model, Principal principal) throws URISyntaxException {

        Meal mealUpdate = null;

        try {

            mealDTO.setUserId(principal.getName());

            mealUpdate = iMealService.saveMeal(mealDTO);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());
            model.addAttribute("mealType", iMealTypeService.getAllMealTypes());

            return "redirect:/user/meal/add";
        }

        model.addAttribute("mealFind", mealUpdate);
        model.addAttribute("mealDTO", new MealDTO());

        return "meal/show";

    }

    @GetMapping("/user/meals/{id}")
    public String showMeal(@PathVariable("id") Long id, Model model) throws URISyntaxException {

        log.debug("showMeal() id: {}", id);

        Meal mealFind = null;

        try {
            mealFind = iMealService.getMeal(id);
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "/user/meals";
        }

        List<Consumable> consumables = iConsumableService.getAllConsumablesByCriteria("", Long.toString(id), "", 0, 4).getContent();

        model.addAttribute("mealFind", mealFind);
        model.addAttribute("consumables", consumables);

        return "meal/show";

    }

    @GetMapping("/user/meals")
    public String showAllMeals(Model model,
            @RequestParam(name = "id", defaultValue = "") Long mealId,
            @RequestParam(name = "type", defaultValue = "") String mealType,
            @RequestParam(name = "user", defaultValue = "") String userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size, Principal principal) throws URISyntaxException {

        log.debug("showAllMeals()");

        Page<Meal> mealPage = null;

        try {

            if (page > 0) {

                page = page - 1;
            }

            mealPage = iMealService.getAllMealsByCriteria(mealId, mealType, page, size, principal.getName());

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

        }

        int totalPages = mealPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("size", size);
        model.addAttribute("mealPage", mealPage);

        return "meal/list";
    }

    @GetMapping("/user/meals/{id}/validate")
    public String validateMeal(@PathVariable("id") Long id, Model model) throws URISyntaxException {

        log.debug("validateMeal() id: {}", id);

        Meal mealFind = null;

        try {
            mealFind = iMealService.validateMeal(id);
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "index";
        }

        //redirectAttributes.addFlashAttribute("msg", "Réservation prolongée !");
        return "redirect:/user/meals/{id}";

    }

    @GetMapping("/user/meals/{id}/delete")
    public String deleteMeal(@PathVariable("id") Long id, Model model) throws URISyntaxException {

        log.debug("deleteMeal() id: {}", id);

        try {

            iMealService.deleteMeal(id);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "index";
        }

        return "redirect:/user/meals";

    }

}
