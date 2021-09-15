package net.nicolasdot.stock_service.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import net.nicolasdot.stock_service.dto.ProduitDTO;
import net.nicolasdot.stock_service.entity.Produit;
import net.nicolasdot.stock_service.exceptions.EntityNotFoundException;
import net.nicolasdot.stock_service.exceptions.NotPossibleException;
import net.nicolasdot.stock_service.specifications.ProduitCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.nicolasdot.stock_service.services.interfaces.IProduitService;
import org.springframework.http.HttpStatus;

/**
 *
 * @author nicolasdotnet
 */
@RestController
@Api(tags = "API pour les opérations de CRUD sur les produits.")
public class ProduitController {

    @Autowired
    IProduitService iProduitService;

    @ApiOperation("Consulter les informations sur un produit grâce à son code barre")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Produit.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 404, message = "le produit n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/product")
    public ResponseEntity<Produit> showProduct(@RequestParam(name = "code")String code, @RequestParam(name = "user") String userId) throws EntityNotFoundException {

//        log.debug("showProduct() code: {}", code);
        Produit produit = iProduitService.consultProduitByCode(code, userId);

        return ResponseEntity.ok(produit);

    }

    @ApiOperation("Consulter les informations sur un produit grâce à son id")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Produit.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 404, message = "le produit n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/produits/{id}")
    public ResponseEntity<Produit> getProduit(@PathVariable("id") int id) throws EntityNotFoundException {

//        log.debug("showProduct() code: {}", code);
        Produit produit = iProduitService.getProduitById(Long.valueOf(id));
        
        return new ResponseEntity<>(produit, HttpStatus.FOUND);

       //return ResponseEntity.ok(produit);

    }

    @ApiOperation("Supprimer un produit de la base de donnée")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Produit.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 404, message = "le produit n'existe pas dans la base"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @DeleteMapping("/api/user/produits/{id}")
    void deleteProduct(@PathVariable("id") int id) throws EntityNotFoundException {

//        log.debug("showProduct() code: {}", code);
        iProduitService.deleteProduit(Long.valueOf(id));

    }

    @ApiOperation("Ajouter un produit dans le stock")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok", response = Produit.class),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @PutMapping("/api/user/produits")
    public ResponseEntity<Produit> saveProduit(@Valid @RequestBody ProduitDTO produitDTO) throws EntityNotFoundException, NotPossibleException {

//        log.debug("showProduct() code: {}", code);
        Produit produit = iProduitService.saveProduit(Long.valueOf(produitDTO.getProduitId()), produitDTO.getUserId());

        return ResponseEntity.ok(produit);

    }

    @ApiOperation("Récupère l'ensemble des produits en fonction du nom, du code et de sa présence dans le stock")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "ok"),
        @ApiResponse(code = 403, message = "vous ne disposez pas des droits pour accéder à la ressource"),
        @ApiResponse(code = 400, message = "erreur de saisie"),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/produits")
    public ResponseEntity<Page<Produit>> showAllProduitsByCriteria(
            @RequestParam(name = "code", defaultValue = "") String code,
            @RequestParam(name = "produit", defaultValue = "") String produit,
            @RequestParam(name = "stock", defaultValue = "") String inStock,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "user") String userId) {

        //log.debug("showAllProductsByCriteria");
        ProduitCriteria productCriteria = new ProduitCriteria();
        productCriteria.setCode(code);
        productCriteria.setProduitName(produit);
        productCriteria.setInStock(inStock);
        productCriteria.setUserId(userId);

        Page<Produit> pageProduit = iProduitService.getAllProduitsByCriteria(productCriteria, page, size);

        return ResponseEntity.ok().body(pageProduit);
    }

    @ApiOperation("Enregistrer les mises à jour disponible sur OFF.")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = ""),
        @ApiResponse(code = 404, message = ""),
        @ApiResponse(code = 401, message = "une authentification est nécessaire")
    })
    @GetMapping("/api/user/produits/update")
    public ResponseEntity getUpdateAllProduits() throws EntityNotFoundException {

        //log.debug("getUpdateProduct dateToday: {}", dateValidate);
        List<Produit> produits = iProduitService.ManagementUpdateProduits();

        return ResponseEntity.ok(produits);
    }

}
