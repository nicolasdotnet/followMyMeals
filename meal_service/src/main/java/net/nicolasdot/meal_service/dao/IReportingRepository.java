package net.nicolasdot.meal_service.dao;

import java.time.LocalDate;
import java.util.Optional;
import net.nicolasdot.meal_service.entity.Reporting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author nicolasdotnet
 */
public interface IReportingRepository extends JpaRepository<Reporting, Long> , JpaSpecificationExecutor<Reporting>{

    public Optional<Reporting> findByUserIdAndToday(String userId, LocalDate today);
    
}
