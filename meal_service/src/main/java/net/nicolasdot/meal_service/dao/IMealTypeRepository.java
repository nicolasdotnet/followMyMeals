package net.nicolasdot.meal_service.dao;

import java.util.Optional;
import net.nicolasdot.meal_service.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface IMealTypeRepository extends JpaRepository<MealType, Long>{
    
    
   Optional<MealType> findByLabel(String label);
    
}
