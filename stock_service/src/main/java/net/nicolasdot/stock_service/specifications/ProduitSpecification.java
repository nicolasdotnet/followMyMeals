package net.nicolasdot.stock_service.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import net.nicolasdot.stock_service.entity.Produit;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet

 This class is used to search Produit specifying the criteria
 *
 */
public class ProduitSpecification implements Specification<Produit> {

    private ProduitCriteria filter;

    public ProduitSpecification(ProduitCriteria filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Produit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getProduitName() != null) {

            String param = "%" + filter.getProduitName() + "%";

            predicate.getExpressions().add(criteriaBuilder.like(root.get("produitName"), param));
            
            System.out.println("CCCCCCCCCCCCCCCCCCCCC produitName : " + filter.getProduitName());
        }

        if (filter.getCode() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("code"), filter.getCode()));

            System.out.println("CCCCCCCCCCCCCCCCCCCCC code : " + filter.getCode());

        }

        return criteriaBuilder.and(predicate);

    }

}
