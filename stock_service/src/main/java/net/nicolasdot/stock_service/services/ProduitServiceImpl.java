package net.nicolasdot.stock_service.services;

import java.time.LocalDate;
import java.util.ArrayList;
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
import net.nicolasdot.stock_service.entity.StockStatus;
import net.nicolasdot.stock_service.exceptions.NotPossibleException;
import pl.coderion.model.Product;
import net.nicolasdot.stock_service.services.interfaces.IProduitService;
import org.springframework.data.domain.Sort;
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
    public Produit consultProduitByCode(String code, String userId) throws EntityNotFoundException {

        Optional<Produit> produitFind = iProduitRepository.findByCodeAndUserId(code, userId);

        if (produitFind.isPresent()) {

            return produitFind.get();
        }

        Optional<Product> productOptional = Proxy.getOpenfoodFact(code);

        if (productOptional.isEmpty()) {

//            log.error("Le produit ns'existe pas !");
            throw new EntityNotFoundException("Le produit n'existe pas !");

        }

        Produit produit = new Produit();
        produit.setUserId(userId);
        produit.setCode(productOptional.get().getCode());
        produit.setBrand(productOptional.get().getBrands());
        produit.setProduitName(productOptional.get().getProductName());
        produit.setImageFrontUrl(productOptional.get().getSelectedImages().getFront().getDisplay().getUrl());
        produit.setLastModifiedT(productOptional.get().getLastModifiedT());
        produit.setIngredientsText(productOptional.get().getIngredientsText());
        produit.setNutritionGrades(productOptional.get().getNutritionGrades());

        ProduitDetails produitDetails = new ProduitDetails();
        produitDetails.setDate(LocalDate.now());
        produitDetails.setInStock(StockStatus.FALSE.getValue());
        produit.setWeight(productOptional.get().getQuantity());

        Nutriment nutriment = new Nutriment();
        Nutriments ns = productOptional.get().getNutriments();
        nutriment.setCarbohydrates100G(ns.getCarbohydrates100G());
        nutriment.setCarbohydratesUnit(ns.getCarbohydratesUnit());
        nutriment.setEnergy100G(ns.getEnergyKcal100G());
        nutriment.setEnergyKjUnit(ns.getEnergyKcalUnit());
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
    public Produit saveProduit(Long produitId, String userId) throws EntityNotFoundException, NotPossibleException {

        Optional<Produit> produitFind = iProduitRepository.findByIdAndUserId(produitId, userId);

        if (produitFind.isEmpty()) {

            throw new EntityNotFoundException("Le produit n'a pas était scanné !");
        }

        switch (produitFind.get().getProduitDetails().getInStock()) {

            case "TRUE":

                produitFind.get().getProduitDetails().setInStock(StockStatus.FALSE.getValue());
                
                return iProduitRepository.saveAndFlush(produitFind.get());

            default:

                produitFind.get().getProduitDetails().setInStock(StockStatus.TRUE.getValue());

                return iProduitRepository.saveAndFlush(produitFind.get());

        }

    }

    @Override
    public Page<Produit> getAllProduitsByCriteria(ProduitCriteria produitCriteria, int page, int size) {

        produitCriteria.setProduitName("".equals(produitCriteria.getProduitName()) ? null : produitCriteria.getProduitName());
        produitCriteria.setCode("".equals(produitCriteria.getCode()) ? null : produitCriteria.getCode());
        produitCriteria.setInStock("".equals(produitCriteria.getInStock()) ? null : produitCriteria.getInStock());
        ProduitSpecification produitSpecification = new ProduitSpecification(produitCriteria);

        return iProduitRepository.findAll(produitSpecification, PageRequest.of(page, size, Sort.Direction.DESC, "id"));
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

}
