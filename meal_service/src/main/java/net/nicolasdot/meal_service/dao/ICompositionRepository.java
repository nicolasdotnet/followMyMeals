package net.nicolasdot.meal_service.dao;

import net.nicolasdot.meal_service.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface ICompositionRepository extends JpaRepository<Composition, Long>  {
    
}
