package net.nicolasdot.stock_service.dao;

import net.nicolasdot.stock_service.entity.ProduitDetails;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author nicolasdotnet
 */
public interface IProduitDetailsRepository extends JpaRepository<ProduitDetails, Long>{
    
}
