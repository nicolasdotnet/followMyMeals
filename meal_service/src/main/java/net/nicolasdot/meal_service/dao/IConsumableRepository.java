package net.nicolasdot.meal_service.dao;

import java.util.Optional;
import net.nicolasdot.meal_service.entity.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author nicolasdotnet
 */
public interface IConsumableRepository extends JpaRepository<Consumable, Long> , JpaSpecificationExecutor<Consumable> {

    @Query(value = "SELECT c FROM Consumable c WHERE c.produitId = :produitId AND c.meal.id = :mealId")
    Optional<Consumable> findByProduitIdAndMealId(@Param("produitId") Long produitId, @Param("mealId") Long mealId);

}
