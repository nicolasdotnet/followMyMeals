package net.nicolasdot.front_service.controllers;

import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.nicolasdot.front_service.beans.Consumable;
import net.nicolasdot.front_service.beans.Produit;
import net.nicolasdot.front_service.dto.ConsuDTO;
import net.nicolasdot.front_service.dto.ConsumableDTO;
import net.nicolasdot.front_service.services.IConsumableService;
import net.nicolasdot.front_service.services.IProduitService;
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
public class ConsumableController {

    private final Logger log = LogManager.getLogger(ConsumableController.class);

    @Autowired
    private IConsumableService iConsumableService;

    @Autowired
    private IProduitService iProduitService;

    @GetMapping("/user/meals/{id}/consumables/add")
    public String showAddConsumableForm(@PathVariable("id") int id, Model model, Principal principal) throws URISyntaxException {

        log.debug("showAddConsumableForm()");

        Page<Produit> produitPage = null;

        try {
            produitPage = iProduitService.getAllProduitsByCriteria("", "", "TRUE", 0, 5, principal.getName());

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

        }

        List<Consumable> consumables = iConsumableService.getAllConsumablesByCriteria("", Integer.toString(id), "", 0, 4).getContent();
        model.addAttribute("consumables", consumables);

        model.addAttribute("produitList", produitPage.getContent());

        ConsuDTO consumableDTO = new ConsuDTO();
        consumableDTO.setMealId(id);
        model.addAttribute("consumableDTO", consumableDTO);

        return "/consumable/addform";

    }

    @PostMapping("/user/consumables")
    public String saveConsumable(@ModelAttribute ConsuDTO consumableDTO, Model model, Principal principal) throws URISyntaxException {

        Consumable consumableUpdate = null;

        ConsumableDTO c = new ConsumableDTO();
        c.setMealId(consumableDTO.getMealId());
        c.setProduitId(Integer.valueOf(consumableDTO.getProduitId().intValue()));
        c.setQuantity(consumableDTO.getQuantity());

        try {

            c.setUserId(principal.getName());
            consumableUpdate = iConsumableService.saveConsumable(c);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "index";
        }

        return "redirect:/user/meals/" + consumableDTO.getMealId() + "/consumables/add";

    }

    @GetMapping("/user/consumables/{id}")
    public String showConsumable(@PathVariable("id") Long id, Model model) throws URISyntaxException {

        log.debug("showConsumable() id: {}", id);

        Consumable consumableFind = null;

        try {
            consumableFind = iConsumableService.getConsumable(id);
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "index";
        }

        model.addAttribute("consumableFind", consumableFind);

        return "consumable/show";

    }

    @GetMapping("/user/consumables")
    public String showAllConsumables(Model model,
            @RequestParam(name = "id", defaultValue = "") String consumableId,
            @RequestParam(name = "meal", defaultValue = "") String mealId,
            @RequestParam(name = "user", defaultValue = "") String userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size, Principal principal) throws URISyntaxException {

        log.debug("showAllConsumables()");

        Page<Consumable> consumablePage = null;

        try {
            consumablePage = iConsumableService.getAllConsumablesByCriteria(consumableId, mealId, principal.getName(), page, size);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

        }

        int totalPages = consumablePage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("size", size);
        model.addAttribute("consumablePage", consumablePage);

        return "consumable/list";
    }

    @GetMapping("/user/consumables/{id}/delete")
    public String removeConsumable(@PathVariable("id") Long id, Model model) throws URISyntaxException {

        log.debug("removeConsumable() id: {}", id);
        
        Consumable consumableFind = null;

        try {
            
            consumableFind = iConsumableService.getConsumable(id);
            
            iConsumableService.removeConsumable(id);
            
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "index";
        }

        return "redirect:/user/meals/" +consumableFind.getMeal().getId()+ "/consumables/add";

    }

}
