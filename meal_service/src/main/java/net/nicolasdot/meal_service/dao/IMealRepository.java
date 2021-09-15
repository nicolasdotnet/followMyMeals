package net.nicolasdot.meal_service.dao;

import java.time.LocalDate;
import java.util.List;
import net.nicolasdot.meal_service.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface IMealRepository extends JpaRepository<Meal, Long> , JpaSpecificationExecutor<Meal> {

  List<Meal> findAllByValidateDateBetweenAndUserId(LocalDate startDate, LocalDate endDate, String userId);

}
