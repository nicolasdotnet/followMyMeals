package net.nicolasdot.front_service.controllers;

import java.net.URISyntaxException;
import java.security.Principal;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.nicolasdot.front_service.beans.Produit;
import net.nicolasdot.front_service.dto.ProduitDTO;
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
public class ProduitController {

    private final Logger log = LogManager.getLogger(ProduitController.class);

    @Autowired
    private IProduitService iProduitService;

    @GetMapping("/user/product")
    public String showProduct(@RequestParam(name = "code") String code, Model model, Principal principal) throws URISyntaxException {

        log.debug("showProduit() code: {}", code);

        Produit produitFind = null;

        try {

            produitFind = iProduitService.getProduitByCode(code, principal.getName());

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "index";
        }

        model.addAttribute("produitFind", produitFind);
        model.addAttribute("produitDetailsDTO", new ProduitDTO());

        return "produit/show";

    }

    @GetMapping("/user/produits/{id}")
    public String showProduitInTheStock(@PathVariable("id") Long id, Model model) throws URISyntaxException {

        log.debug("showProduitInTheStock() id: {}", id);

        Produit produitFind = null;

        try {
            produitFind = iProduitService.getProduit(id);
        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "/user/produits";
        }

        model.addAttribute("produitFind", produitFind);
        model.addAttribute("produitDTO", new ProduitDTO());

        return "produit/show";

    }

    @GetMapping("/user/produits")
    public String showAllProduits(Model model,
            @RequestParam(name = "code", defaultValue = "") String code,
            @RequestParam(name = "produit", defaultValue = "") String produit,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size, Principal principal) throws URISyntaxException {

        log.debug("showAllProduits()");

        Page<Produit> produitPage = null;
        String inStock = "";

        try {

            if (page > 0) {

                page = page - 1;
            }

            produitPage = iProduitService.getAllProduitsByCriteria(code, produit, inStock, page, size, principal.getName());

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

        }

        int totalPages = produitPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("size", size);
        model.addAttribute("produitPage", produitPage);

        return "produit/list";
    }

    @GetMapping("/user/stock")
    public String getAllProduitsFromStock(Model model,
            @RequestParam(name = "code", defaultValue = "") String code,
            @RequestParam(name = "produit", defaultValue = "") String produit,
            @RequestParam(name = "stock", defaultValue = "TRUE") String inStock,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size, Principal principal) throws URISyntaxException {

        log.debug("showAllProduits()");

        Page<Produit> produitPage = null;

        try {

            if (page > 0) {

                page = page - 1;
            }

            System.out.println("PPPPPPPPPPPPPPPPPAGE : " + page);

            produitPage = iProduitService.getAllProduitsByCriteria(code, produit, inStock, page, size, principal.getName());

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

        }

        int totalPages = produitPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("size", size);
        model.addAttribute("produitPage", produitPage);

        return "produit/stock";
    }

    @GetMapping("/user/produit/multisearch")
    public String multisearch(Model model) {

        log.debug("multisearch()");

        return "produit/multisearch";
    }

    @PostMapping("/user/produit")
    public String saveProduit(@ModelAttribute ProduitDTO produitDTO, Model model, Principal principal) throws URISyntaxException {

        System.out.println("FFFFFFFFFFFFFFFFFFFFF)" + produitDTO.getProduitId());

        Produit produitUpdate = null;

        try {

            produitDTO.setUserId(principal.getName());

            produitUpdate = iProduitService.saveProduitInStock(produitDTO);

        } catch (HttpClientErrorException e) {

            model.addAttribute("error", Tools.messageExtraction(e).getMessage());

            return "redirect:/user/produits/" + produitDTO.getProduitId();
        }

        model.addAttribute("produitFind", produitUpdate);
        model.addAttribute("produitDTO", new ProduitDTO());

        return "produit/show";

    }
}
