package net.nicolasdot.stock_service.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.nicolasdot.stock_service.dao.IProduitRepository;
import net.nicolasdot.stock_service.entity.Nutriment;
import net.nicolasdot.stock_service.entity.Produit;
import net.nicolasdot.stock_service.entity.ProduitDetails;
import net.nicolasdot.stock_service.exceptions.EntityNotFoundException;
import net.nicolasdot.stock_service.exceptions.NotPossibleException;
import net.nicolasdot.stock_service.tools.Proxy;
import static net.nicolasdot.stock_service.tools.Proxy.getOpenfoodFact;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderion.model.Nutriments;
import pl.coderion.model.Product;
import pl.coderion.model.SelectedImage;
import pl.coderion.model.SelectedImageItem;
import pl.coderion.model.SelectedImages;

/**
 *
 * @author nicolasdotnet
 */
@ExtendWith(MockitoExtension.class)
public class ProduitServiceImplUTest {

    @InjectMocks
    private ProduitServiceImpl instance;

    @Mock
    private IProduitRepository iProduitRepository;

    private MockedStatic<Proxy> proxyMock;

    @BeforeEach
    public void setup() {

        proxyMock = Mockito.mockStatic(Proxy.class);

    }

    @AfterEach
    public void close() {

        proxyMock.close();

    }

    /**
     * Test of consultProduitByCode method, of class ProduitServiceImpl.
     */
    @Test
    public void testConsultProductByCodeWhenTheProduitIsAlreadyExist() throws Exception {
        System.out.println("testConsultProductByCodeWhenTheProduitIsAlreadyExist");
        String code = "000";

        Produit expResult = new Produit();
        expResult.setCode(code);

        Optional<Produit> op = Optional.of(expResult);

        when(iProduitRepository.findByCode(code)).thenReturn(op);

        Produit result = instance.consultProduitByCode(code);
        assertEquals(expResult.getCode(), result.getCode());

        verify(iProduitRepository).findByCode(code);

    }

    /**
     * Test of consultProduitByCode method, of class ProduitServiceImpl.
     */
    @Test
    public void testConsultProductByCodeWhenTheProduitDoesNotExit() throws Exception {
        System.out.println("testConsultProductByCodeWhenTheProduitDoesNotExit");
        String code = "000";

        Produit expResult = new Produit();
        expResult.setCode(code);
        Optional<Produit> op = Optional.empty();
        when(iProduitRepository.findByCode(code)).thenReturn(op);

        Optional<Product> opProduct = Optional.empty();
        //MockedStatic<Proxy> proxyMock = Mockito.mockStatic(Proxy.class);
        proxyMock.when(() -> getOpenfoodFact(code)).thenReturn(opProduct);

        try {

            instance.consultProduitByCode(code);

        } catch (Exception e) {

            assertTrue(e instanceof EntityNotFoundException);
            assertEquals(e.getMessage(), "Le produit n'existe pas !");
            verify(iProduitRepository).findByCode(code);
            proxyMock.verify(() -> getOpenfoodFact(code));

        }

        //proxyMock.close();
    }

    /**
     * Test of consultProduitByCode method, of class ProduitServiceImpl.
     */
    @Test
    public void testConsultProductByCodeWhenCodeIsGood() throws Exception {
        System.out.println("testConsultProductByCodeWhenCodeIsGood");
        String code = "000";

        Optional<Produit> op = Optional.empty();
        when(iProduitRepository.findByCode(code)).thenReturn(op);

        SelectedImages selectedImages = new SelectedImages();
        SelectedImage selectedImage = new SelectedImage();
        selectedImages.setFront(selectedImage);
        SelectedImageItem selectedImageItem = new SelectedImageItem();
        selectedImageItem.setFr("fr");
        selectedImage.setDisplay(selectedImageItem);

        Product product = new Product();
        product.setCode("000");
        product.setProductName("xxxx");
        product.setBrands("Brand");
        product.setLastModifiedT(0000L);
        product.setSelectedImages(selectedImages);

        product.setSelectedImages(selectedImages);

        product.setQuantity("0g");
        product.setIngredientsText("xxxx");
        product.setNutritionGrades("x");

        Nutriments nutriments = new Nutriments();

        nutriments.setCarbohydrates100G(0);
        nutriments.setCarbohydratesUnit("g");
        nutriments.setEnergy100G(0);
        nutriments.setEnergyKjUnit("g");
        nutriments.setFat100G(0);
        nutriments.setFatUnit("g");
        nutriments.setFiber100G(0);
        nutriments.setFiberUnit("g");
        nutriments.setProteins100G(0);
        nutriments.setProteinsUnit("g");
        nutriments.setSalt100G(0);
        nutriments.setSaltUnit("g");
        nutriments.setSaturatedFat100G(0);
        nutriments.setSaturatedFatUnit("g");
        nutriments.setSugars100G(0);
        nutriments.setSugarsUnit("g");
        nutriments.setCalcium100G(0);
        nutriments.setCalciumUnit("g");
        nutriments.setIron100G(0);
        nutriments.setIronUnit("g");

        nutriments.setVitaminA100G(0);
        nutriments.setVitaminAUnit("g");
        nutriments.setVitaminC100G(0);
        nutriments.setVitaminCUnit("g");
        nutriments.setVitaminD100G(0);
        nutriments.setVitaminDUnit("g");

        product.setNutriments(nutriments);

        Optional<Product> opProduct = Optional.of(product);
        proxyMock.when(() -> getOpenfoodFact(code)).thenReturn(opProduct);

        ProduitDetails produitDetails = new ProduitDetails();
        produitDetails.setDate(LocalDate.now());
        produitDetails.setInStock(Boolean.FALSE);

        Produit expResult = new Produit();
        expResult.setCode(code);
        expResult.setProduitDetails(produitDetails);

        when(iProduitRepository.save(any())).thenReturn(expResult);

        Produit result = instance.consultProduitByCode(code);
        assertEquals(expResult.getCode(), result.getCode());

        verify(iProduitRepository).findByCode(code);
        proxyMock.verify(() -> getOpenfoodFact(code));

    }

    /**
     * Test of getProduitById method, of class ProduitServiceImpl.
     */
    @Test
    public void testGetProductByIdWhenIdIsGood() throws Exception {
        System.out.println("testGetProductByIdWhenIdIsGood");

        Long id = 99L;

        Produit expResult = new Produit();
        expResult.setCode("000");
        Optional<Produit> op = Optional.of(expResult);

        when(iProduitRepository.findById(id)).thenReturn(op);

        Produit result = instance.getProduitById(id);
        assertEquals(expResult.getCode(), result.getCode());

        verify(iProduitRepository).findById(id);

    }

    /**
     * Test of getProduitById method, of class ProduitServiceImpl.
     */
    @Test
    public void testGetProduitByIdWhenTheProduitDoesNotExit() throws Exception {
        System.out.println("testGetProduitByIdWhenTheProduitDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Long id = 99L;

                    Optional<Produit> op = Optional.empty();

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.getProduitById(id);
                });

        String expectedMessage = "Le produit n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of deleteProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testDeleteProduitWhenTheProduitDoesNotExit() throws EntityNotFoundException {
        System.out.println("testDeleteProduitWhenTheProduitDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Long id = 99L;

                    Optional<Produit> op = Optional.empty();

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.deleteProduit(id);
                });

        String expectedMessage = "Le produit n'existe pas !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of deleteProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testDeleteProduitWhenProduitExist() throws EntityNotFoundException {
        System.out.println("testDeleteProduitWhenProduitExist");

        Long id = 99L;

        Optional<Produit> op = Optional.of(new Produit());

        when(iProduitRepository.findById(id)).thenReturn(op);

        instance.deleteProduit(id);

        verify(iProduitRepository).delete(any());
    }

    /**
     * Test of saveProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testSaveProduitWhenTheProduitDoesNotExit() throws EntityNotFoundException {
        System.out.println("testSaveProduitWhenTheProduitDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Long id = 99L;

                    Optional<Produit> op = Optional.empty();

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.saveProduit(id, 0);
                });

        String expectedMessage = "Le produit n'a pas était scanné !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test of saveProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testSaveProduitWhenQuantityIsNegative() throws NotPossibleException {
        System.out.println("testSaveProduitWhenQuantityIsNegative");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long id = 99L;
                    int quantity = -10;

                    Produit produit = new Produit();
                    Optional<Produit> op = Optional.of(produit);

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.saveProduit(id, quantity);
                });

        String expectedMessage = "Il n'y a pas de quantité";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of saveProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testSaveProduitWhenQuantityIsZero() throws NotPossibleException {
        System.out.println("testSaveProduitWhenQuantityIsZero");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long id = 99L;
                    int quantity = 0;

                    Produit produit = new Produit();
                    Optional<Produit> op = Optional.of(produit);

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.saveProduit(id, quantity);
                });

        String expectedMessage = "Il n'y a pas de quantité";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of saveProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testSaveProduitWhenStockIsTrue() throws Exception {
        System.out.println("testSaveProduitWHenStockIsTrue");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long id = 99L;
                    int quantity = 10;

                    ProduitDetails produitDetails = new ProduitDetails();
                    produitDetails.setInStock(Boolean.TRUE);

                    Produit produit = new Produit();
                    produit.setProduitDetails(produitDetails);
                    Optional<Produit> op = Optional.of(produit);

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.saveProduit(id, quantity);
                });

        String expectedMessage = "Le produit est déjà dans le stock";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    /**
     * Test of saveProduit method, of class ProduitServiceImpl.
     */
    @Test
    public void testSaveProduitStockFalseAndQuantityPositive() throws Exception {
        System.out.println("testSaveProduitStockFalseAndQuantityPositif");

        Long id = 99L;
        int quantity = 10;

        ProduitDetails produitDetails = new ProduitDetails();
        produitDetails.setInStock(Boolean.FALSE);
        produitDetails.setQuantity(0);
        produitDetails.setWeight(0);
        produitDetails.setWeightUnit("unit");
        produitDetails.setRemainingQuantity(0);

        Produit produit = new Produit();
        produit.setWeight("200g");
        produit.setProduitDetails(produitDetails);
        Optional<Produit> op = Optional.of(produit);

        when(iProduitRepository.findById(id)).thenReturn(op);

        ProduitDetails expResultDetails = new ProduitDetails();
        expResultDetails.setInStock(Boolean.TRUE);
        expResultDetails.setQuantity(10);
        expResultDetails.setWeight(0);
        expResultDetails.setWeightUnit("unit");
        expResultDetails.setRemainingQuantity(0);

        Produit expResult = new Produit();
        expResult.setProduitDetails(expResultDetails);

        when(iProduitRepository.saveAndFlush(any())).thenReturn(expResult);

        Produit result = instance.saveProduit(id, quantity);
        assertEquals(expResult.getProduitDetails().getQuantity(), result.getProduitDetails().getQuantity());
        verify(iProduitRepository).findById(id);

    }

    /**
     * Test of ManagementOfProduits method, of class ProduitServiceImpl.
     */
    @Test
    public void testManagementOfProductsWhenNoUpdate() throws Exception {
        System.out.println("testManagementOfProductsWhenNoUpdate");

        Produit produit = new Produit();
        produit.setLastModifiedT(001L);
        List<Produit> produitsFind = new ArrayList<>();
        produitsFind.add(produit);
        when(iProduitRepository.findAllByInStock(Boolean.TRUE)).thenReturn(produitsFind);

        Product product = new Product();
        product.setLastModifiedT(001L);
        Optional<Product> productFind = Optional.of(product);
        proxyMock.when(() -> getOpenfoodFact(any())).thenReturn(productFind);

        List<Produit> expResult = new ArrayList<>();

        List<Produit> result = instance.ManagementUpdateProduits();
        assertEquals(expResult, result);
        verify(iProduitRepository).findAllByInStock(Boolean.TRUE);
        proxyMock.verify(() -> getOpenfoodFact(any()));

    }

    /**
     * Test of ManagementOfProduits method, of class ProduitServiceImpl.
     */
    @Test
    public void testManagementOfProductsWhenOneUpdate() throws Exception {
        System.out.println("ManagementOfProducts");

        List<Produit> produitsFind = new ArrayList<>();
        Produit produit = new Produit();
        produit.setLastModifiedT(001L);
        produit.setNutriment(new Nutriment());
        produitsFind.add(produit);
        when(iProduitRepository.findAllByInStock(Boolean.TRUE)).thenReturn(produitsFind);

        SelectedImages selectedImages = new SelectedImages();
        SelectedImage selectedImage = new SelectedImage();
        selectedImages.setFront(selectedImage);
        SelectedImageItem selectedImageItem = new SelectedImageItem();
        selectedImageItem.setFr("fr");
        selectedImage.setDisplay(selectedImageItem);

        Product product = new Product();
        product.setLastModifiedT(002L);
        product.setCode("000");
        product.setProductName("xxxx");
        product.setSelectedImages(selectedImages);
        product.setIngredientsText("xxxx");
        product.setNutritionGrades("x");

        Nutriments nutriments = new Nutriments();

        nutriments.setCarbohydrates100G(0);
        nutriments.setCarbohydratesUnit("g");
        nutriments.setEnergy100G(0);
        nutriments.setEnergyKjUnit("g");
        nutriments.setFat100G(0);
        nutriments.setFatUnit("g");
        nutriments.setFiber100G(0);
        nutriments.setFiberUnit("g");
        nutriments.setProteins100G(0);
        nutriments.setProteinsUnit("g");
        nutriments.setSalt100G(0);
        nutriments.setSaltUnit("g");
        nutriments.setSaturatedFat100G(0);
        nutriments.setSaturatedFatUnit("g");
        nutriments.setSugars100G(0);
        nutriments.setSugarsUnit("g");
        nutriments.setCalcium100G(0);
        nutriments.setCalciumUnit("g");
        nutriments.setIron100G(0);
        nutriments.setIronUnit("g");

        nutriments.setVitaminA100G(0);
        nutriments.setVitaminAUnit("g");
        nutriments.setVitaminC100G(0);
        nutriments.setVitaminCUnit("g");
        nutriments.setVitaminD100G(0);
        nutriments.setVitaminDUnit("g");

        product.setNutriments(nutriments);

        Optional<Product> productFind = Optional.of(product);
        proxyMock.when(() -> getOpenfoodFact(any())).thenReturn(productFind);

        when(iProduitRepository.saveAndFlush(any())).thenReturn(produit);

        List<Produit> result = instance.ManagementUpdateProduits();

        assertEquals(Boolean.FALSE, result.isEmpty());

        verify(iProduitRepository).findAllByInStock(Boolean.TRUE);
        proxyMock.verify(() -> getOpenfoodFact(any()));

    }

    @Test
    public void testExtractQuantityWhenQuantityAndUnit() {

        int quantity = 2;

        String quantityP = "444g";

        float result = instance.extractQuatity(quantityP);

        assertEquals(444.00, result);
    }

    @Test
    public void testExtractQuantityWhenQuantityAndIntervalAndUnit() {

        String quantityP = "444 g";

        float result = instance.extractQuatity(quantityP);

        assertEquals(444.00, result);
    }

    @Test
    public void testExtractUnitWhenQuantityAndIntervalAndUnit() {

        String quantityP = "444 g";

        String result = instance.extractUnit(quantityP);

        assertEquals("g", result);
    }

    @Test
    public void testExtractUnitWhenQuantityAndUnit() {

        String quantityP = "444g";

        String result = instance.extractUnit(quantityP);

        assertEquals("g", result);
    }

    @Test
    public void testUpdateQuantityProduitWhenTheProduitDoesNotExit() {

        System.out.println("testUpdateQuantityProduitWhenTheProduitDoesNotExit");

        Exception exception = assertThrows(EntityNotFoundException.class,
                () -> {

                    Long id = 99L;

                    Optional<Produit> op = Optional.empty();

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.updateQuantityProduit(id, 0);
                });

        String expectedMessage = "Le produit n'a pas était scanné !";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateQuantityProduitWhenQuantityIsZero() {

        System.out.println("testUpdateQuantityProduitWhenQuantityIsZero");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long id = 99L;

                    Optional<Produit> op = Optional.of(new Produit());

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.updateQuantityProduit(id, 0);
                });

        String expectedMessage = "Il n'y a pas de quantité";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateQuantityProduitWhenQuantityIsNegative() {

        System.out.println("testUpdateQuantityProduitWhenQuantityIsNegative");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long id = 99L;

                    Optional<Produit> op = Optional.of(new Produit());

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.updateQuantityProduit(id, -1);
                });

        String expectedMessage = "Il n'y a pas de quantité";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateQuantityProduitWhenTheProduitDoesNotExitInTheStock() {

        System.out.println("testUpdateQuantityProduitWhenTheProduitDoesNotExitInTheStock");

        Exception exception = assertThrows(NotPossibleException.class,
                () -> {

                    Long id = 99L;

                    ProduitDetails produitDetails = new ProduitDetails();
                    produitDetails.setInStock(Boolean.FALSE);

                    Produit produit = new Produit();
                    produit.setProduitDetails(produitDetails);

                    Optional<Produit> op = Optional.of(produit);

                    when(iProduitRepository.findById(id)).thenReturn(op);

                    instance.updateQuantityProduit(id, 1);
                });

        String expectedMessage = "Le produit n'est pas dans le stock";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testUpdateQuantityProduitWhenQuantityISPositive() throws EntityNotFoundException, NotPossibleException {

        System.out.println("testUpdateQuantityProduitWhenQuantityISPositive");

        Long id = 99L;

        ProduitDetails produitDetails = new ProduitDetails();
        produitDetails.setInStock(Boolean.TRUE);
        produitDetails.setQuantity(5);

        Produit produit = new Produit();
        produit.setProduitDetails(produitDetails);

        Optional<Produit> op = Optional.of(produit);

        when(iProduitRepository.findById(id)).thenReturn(op);

        ProduitDetails expResultDetails = new ProduitDetails();
        expResultDetails.setInStock(Boolean.TRUE);
        expResultDetails.setQuantity(1);

        Produit expResult = new Produit();
        expResult.setProduitDetails(expResultDetails);

        when(iProduitRepository.saveAndFlush(any())).thenReturn(expResult);

        Produit result = instance.updateQuantityProduit(id, 1);

        assertEquals(expResult.getProduitDetails().getQuantity(), result.getProduitDetails().getQuantity());
        verify(iProduitRepository).findById(id);
    }

}
