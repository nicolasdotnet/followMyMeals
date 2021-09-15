package net.nicolasdot.meal_service.dao;



import net.nicolasdot.meal_service.entity.ConsumableNutriment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface IConsumableNutrimentRepository extends JpaRepository<ConsumableNutriment, Long>{
    
}
