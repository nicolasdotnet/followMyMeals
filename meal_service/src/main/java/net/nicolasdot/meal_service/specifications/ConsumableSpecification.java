package net.nicolasdot.meal_service.specifications;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import net.nicolasdot.meal_service.entity.Consumable;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author nicolasdotnet This class is used to search Meal specifying the
 * criteria
 *
 */
public class ConsumableSpecification implements Specification<Consumable> {

    private ConsumableCriteria filter;

    public ConsumableSpecification(ConsumableCriteria filter) {
        super();
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Consumable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (filter.getConsumableId() != null) {
             predicate.getExpressions().add(criteriaBuilder.equal(root.get("id"), filter.getConsumableId()));

            System.out.println("CCCCCCCCCCCCCCCCCCCCC getMealtype() : " + filter.getConsumableId());
        }

        if (filter.getMealId() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.join("meal").get("id"), filter.getMealId()));

            System.out.println("CCCCCCCCCCCCCCCCCCCCC id : " + filter.getMealId());

        }

        if (filter.getUserId() != null) {
            predicate.getExpressions().add(criteriaBuilder.equal(root.get("userId"), filter.getUserId()));

            System.out.println("CCCCCCCCCCCCCCCCCCCCC userId : " + filter.getUserId());

        }

        return criteriaBuilder.and(predicate);

    }

}
