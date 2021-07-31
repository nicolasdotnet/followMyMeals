package net.nicolasdot.stock_service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import net.nicolasdot.stock_service.entity.Produit;
import net.nicolasdot.stock_service.entity.ProduitDetails;
import net.nicolasdot.stock_service.exceptions.EntityNotFoundException;
import net.nicolasdot.stock_service.specifications.ProduitCriteria;
import net.nicolasdot.stock_service.specifications.ProduitSpecification;
import net.nicolasdot.stock_service.tools.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import net.nicolasdot.stock_service.dao.IProduitRepository;
import net.nicolasdot.stock_service.entity.Nutriment;
import net.nicolasdot.stock_service.exceptions.NotPossibleException;
import pl.coderion.model.Product;
import net.nicolasdot.stock_service.services.interfaces.IProduitService;
import pl.coderion.model.Nutriments;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class ProduitServiceImpl implements IProduitService {

    @Autowired
    private IProduitRepository iProduitRepository;

    //TODO
    //  private static final Logger log = LogManager.getLogger(ProduitServiceImpl.class);
    @Override
    public Produit consultProduitByCode(String code) throws EntityNotFoundException {

        Optional<Produit> produitFind = iProduitRepository.findByCode(code);

        if (produitFind.isPresent()) {

            return produitFind.get();
        }

        Optional<Product> productOptional = Proxy.getOpenfoodFact(code);

        if (productOptional.isEmpty()) {

//            log.error("Le produit ns'existe pas !");
            throw new EntityNotFoundException("Le produit n'existe pas !");

        }

        Produit produit = new Produit();
        produit.setCode(productOptional.get().getCode());
        produit.setBrand(productOptional.get().getBrands());
        produit.setProduitName(productOptional.get().getProductName());
        produit.setImageFrontUrl(productOptional.get().getSelectedImages().getFront().getDisplay().getUrl());
        produit.setLastModifiedT(productOptional.get().getLastModifiedT());
        produit.setIngredientsText(productOptional.get().getIngredientsText());
        produit.setNutritionGrades(productOptional.get().getNutritionGrades());

        ProduitDetails produitDetails = new ProduitDetails();
        produitDetails.setDate(LocalDate.now());
        produitDetails.setInStock(Boolean.FALSE);
        produit.setWeight(productOptional.get().getQuantity());

        Nutriment nutriment = new Nutriment();
        Nutriments ns = productOptional.get().getNutriments();
        nutriment.setCarbohydrates100G(ns.getCarbohydrates100G());
        nutriment.setCarbohydratesUnit(ns.getCarbohydratesUnit());
        nutriment.setEnergy100G(ns.getEnergy100G());
        nutriment.setEnergyKjUnit(ns.getEnergyKjUnit());
        nutriment.setFat100G(ns.getFat100G());
        nutriment.setFatUnit(ns.getFatUnit());
        nutriment.setFiber100G(ns.getFiber100G());
        nutriment.setFiberUnit(ns.getFiberUnit());
        nutriment.setProteins100G(ns.getProteins100G());
        nutriment.setProteinsUnit(ns.getProteinsUnit());
        nutriment.setSalt100G(ns.getSalt100G());
        nutriment.setSaltUnit(ns.getProteinsUnit());
        nutriment.setSaturatedFat100G(ns.getSaturatedFat100G());
        nutriment.setSaturatedFatUnit(ns.getSaturatedFatUnit());
        nutriment.setSugars100G(ns.getSugars100G());
        nutriment.setSugarsUnit(ns.getSugarsUnit());
        nutriment.setCalcium100G(ns.getCalcium100G());
        nutriment.setCalciumUnit(ns.getCalciumUnit());
        nutriment.setIron100G(ns.getIron100G());
        nutriment.setIronUnit(ns.getIronUnit());

        nutriment.setVitaminA100G(ns.getVitaminA100G());
        nutriment.setVitaminAUnit(ns.getVitaminAUnit());
        nutriment.setVitaminC100G(ns.getVitaminC100G());
        nutriment.setVitaminCUnit(ns.getVitaminCUnit());
        nutriment.setVitaminD100G(ns.getVitaminD100G());
        nutriment.setVitaminDUnit(ns.getVitaminDUnit());

        produit.setProduitDetails(produitDetails);
        produitDetails.setProduit(produit);

        produit.setNutriment(nutriment);
        nutriment.setProduit(produit);

        return iProduitRepository.save(produit);
    }

    @Override
    public Produit getProduitById(Long id) throws EntityNotFoundException {

        Optional<Produit> productFind = iProduitRepository.findById(id);

        if (!productFind.isPresent()) {

            throw new EntityNotFoundException("Le produit n'existe pas !");
        }

        return productFind.get();
    }

    @Override
    public void deleteProduit(Long id) throws EntityNotFoundException {

        Optional<Produit> productFind = iProduitRepository.findById(id);

        if (productFind.isEmpty()) {

            throw new EntityNotFoundException("Le produit n'existe pas !");
        }

        iProduitRepository.delete(productFind.get());

    }

    @Override
    public Produit saveProduit(Long id, int quantity) throws EntityNotFoundException, NotPossibleException {

        Optional<Produit> produitFind = iProduitRepository.findById(id);

        if (produitFind.isEmpty()) {

            throw new EntityNotFoundException("Le produit n'a pas était scanné !");
        }

        if (quantity <= 0) {

            throw new NotPossibleException("Il n'y a pas de quantité");
        }

        switch (produitFind.get().getProduitDetails().getInStock().toString()) {

            case "true":

                throw new NotPossibleException("Le produit est déjà dans le stock");

            default:

//                float weight = this.extractQuatity(produitFind.get().getWeight());
//                String weightUnit = this.extractUnit(produitFind.get().getWeight());

                produitFind.get().getProduitDetails().setInStock(Boolean.TRUE);
                produitFind.get().getProduitDetails().setQuantity(quantity);
//                produitFind.get().getProduitDetails().setWeight(weight);
//                produitFind.get().getProduitDetails().setWeightUnit(weightUnit);
//                produitFind.get().getProduitDetails().setRemainingQuantity((float) quantity * weight);

                return iProduitRepository.saveAndFlush(produitFind.get());

        }

    }

    @Override
    public Produit updateQuantityProduit(Long id, int quantity) throws EntityNotFoundException, NotPossibleException {

        Optional<Produit> produitFind = iProduitRepository.findById(id);

        if (produitFind.isEmpty()) {

            throw new EntityNotFoundException("Le produit n'a pas était scanné !");
        }

        if (quantity <= 0) {

            throw new NotPossibleException("Il n'y a pas de quantité");
        }

        switch (produitFind.get().getProduitDetails().getInStock().toString()) {

            case "true":

                produitFind.get().getProduitDetails().setQuantity(quantity);

                return iProduitRepository.saveAndFlush(produitFind.get());

            default:

                throw new NotPossibleException("Le produit n'est pas dans le stock");

        }

    }

    @Override
    public Page<Produit> getAllProduitsByCriteria(ProduitCriteria produitCriteria, int page, int size) {

        produitCriteria.setProduitName("".equals(produitCriteria.getProduitName()) ? null : produitCriteria.getProduitName());
        produitCriteria.setCode("".equals(produitCriteria.getCode()) ? null : produitCriteria.getCode());

        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB code : " + produitCriteria.getCode());
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB produitName : " + produitCriteria.getProduitName());

        ProduitSpecification produitSpecification = new ProduitSpecification(produitCriteria);
        
        System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBB produitSpecification : " +  produitSpecification.toString());

        return iProduitRepository.findAll(produitSpecification, PageRequest.of(page, size));
    }

    @Override
    public List<Produit> ManagementUpdateProduits() throws EntityNotFoundException {

        List<Produit> produitUpdate = new ArrayList<Produit>();

        List<Produit> produitsFind = iProduitRepository.findAllByInStock(Boolean.TRUE);

        for (Produit produit : produitsFind) {

            Optional<Product> productFind = Proxy.getOpenfoodFact(produit.getCode());

            if (productFind.get().getLastModifiedT() <= produit.getLastModifiedT()) {

            } else {

                produit.setCode(productFind.get().getCode());
                produit.setBrand(productFind.get().getBrands());
                produit.setProduitName(productFind.get().getProductName());
                produit.setImageFrontUrl(productFind.get().getSelectedImages().getFront().getDisplay().getUrl());
                produit.setLastModifiedT(productFind.get().getLastModifiedT());
                produit.setIngredientsText(productFind.get().getIngredientsText());
                produit.setNutritionGrades(productFind.get().getNutritionGrades());
                produit.setWeight(productFind.get().getQuantity());

                Nutriment nutriment = produit.getNutriment();
                Nutriments ns = productFind.get().getNutriments();
                nutriment.setCarbohydrates100G(ns.getCarbohydrates100G());
                nutriment.setCarbohydratesUnit(ns.getCarbohydratesUnit());
                nutriment.setEnergy100G(ns.getEnergy100G());
                nutriment.setEnergyKjUnit(ns.getEnergyKjUnit());
                nutriment.setFat100G(ns.getFat100G());
                nutriment.setFatUnit(ns.getFatUnit());
                nutriment.setFiber100G(ns.getFiber100G());
                nutriment.setFiberUnit(ns.getFiberUnit());
                nutriment.setProteins100G(ns.getProteins100G());
                nutriment.setProteinsUnit(ns.getProteinsUnit());
                nutriment.setSalt100G(ns.getSalt100G());
                nutriment.setSaltUnit(ns.getProteinsUnit());
                nutriment.setSaturatedFat100G(ns.getSaturatedFat100G());
                nutriment.setSaturatedFatUnit(ns.getSaturatedFatUnit());
                nutriment.setSugars100G(ns.getSugars100G());
                nutriment.setSugarsUnit(ns.getSugarsUnit());
                nutriment.setCalcium100G(ns.getCalcium100G());
                nutriment.setCalciumUnit(ns.getCalciumUnit());
                nutriment.setIron100G(ns.getIron100G());
                nutriment.setIronUnit(ns.getIronUnit());

                nutriment.setVitaminA100G(ns.getVitaminA100G());
                nutriment.setVitaminAUnit(ns.getVitaminAUnit());
                nutriment.setVitaminC100G(ns.getVitaminC100G());
                nutriment.setVitaminCUnit(ns.getVitaminCUnit());
                nutriment.setVitaminD100G(ns.getVitaminD100G());
                nutriment.setVitaminDUnit(ns.getVitaminDUnit());

                produit.setNutriment(nutriment);
                nutriment.setProduit(produit);

                produitUpdate.add(produit);
                iProduitRepository.saveAndFlush(produit);

            }

        }
        return produitUpdate;

    }

    public float extractQuatity(String quantityP) {

        // Remplacer chaque nombre non numérique par un espace
        quantityP = quantityP.replaceAll("[^\\d]", " ");
        // Supprimer les espaces de début et de fin 
        quantityP = quantityP.trim();
        // Remplacez les espaces consécutifs par un seul espace
        quantityP = quantityP.replaceAll(" +", " ");

        return Float.parseFloat(quantityP);

    }

    public String extractUnit(String quantityP) {

        quantityP = quantityP.replaceAll("[^a-z]", " ");
        // Supprimer les espaces de début et de fin 
        quantityP = quantityP.trim();
        // Remplacez les espaces consécutifs par un seul espace
        quantityP = quantityP.replaceAll(" +", " ");

        return quantityP;

    }

}
