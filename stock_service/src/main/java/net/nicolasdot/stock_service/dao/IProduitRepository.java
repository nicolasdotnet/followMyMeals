package net.nicolasdot.stock_service.dao;

import java.util.List;
import java.util.Optional;
import net.nicolasdot.stock_service.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nicolasdotnet
 */
public interface IProduitRepository extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {

    @Query(value = "SELECT p FROM Produit p WHERE p.produitDetails.inStock = :inStock")
    List<Produit> findAllByInStock(@Param("inStock")Boolean inStock);

    Optional<Produit> findByIdAndUserId(Long produitId, String userId);

    Optional<Produit> findByCodeAndUserId(String code, String userId);
    
}
