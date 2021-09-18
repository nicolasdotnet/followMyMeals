package net.nicolasdot.meal_service.services;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import net.nicolasdot.meal_service.dao.IMealTypeRepository;
import net.nicolasdot.meal_service.dto.MealTypeDTO;
import net.nicolasdot.meal_service.entity.MealType;
import net.nicolasdot.meal_service.exceptions.EntityAlreadyExistsException;
import net.nicolasdot.meal_service.services.interfaces.IMealTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nicolasdotnet
 */
@Service
@Transactional
public class MealTypeServiceImpl implements IMealTypeService {

    @Autowired
    IMealTypeRepository iMealTypeRepository;
    
    private static final Logger log = LogManager.getLogger(MealServiceImpl.class);

    @Override
    public MealType register(MealTypeDTO mealTypeDTO) throws EntityAlreadyExistsException{

        Optional<MealType> mealTypeFind = iMealTypeRepository.findByLabel(mealTypeDTO.getLabel());

        if (mealTypeFind.isPresent()) {

             log.error("Le type de repas existe déjà !");
            throw new EntityAlreadyExistsException("Le type de repas existe déjà !");

        }

        MealType mealType = new MealType();
        mealType.setLabel(mealTypeDTO.getLabel());

        return iMealTypeRepository.save(mealType);
    }

    @Override
    public Optional<MealType> getMealType(Long label) {

        return iMealTypeRepository.findById(label);

    }

    @Override
    public List<MealType> getAllMealTypes() {
        return iMealTypeRepository.findAll();
    }

}
