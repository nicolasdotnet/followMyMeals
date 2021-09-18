package net.nicolasdot.meal_service.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import net.nicolasdot.meal_service.dao.IReportingRepository;
import net.nicolasdot.meal_service.entity.Composition;
import net.nicolasdot.meal_service.entity.Meal;
import net.nicolasdot.meal_service.entity.Reporting;
import net.nicolasdot.meal_service.exceptions.ReportingNotPossibleException;
import net.nicolasdot.meal_service.services.interfaces.IMealService;
import net.nicolasdot.meal_service.services.interfaces.IReportingService;
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
public class ReportingServiceImpl implements IReportingService {

    @Autowired
    IMealService iMealService;

    @Autowired
    IReportingRepository iReportingRepository;

    private static final Logger log = LogManager.getLogger(ReportingServiceImpl.class);

    public Reporting reporting(String userId) throws ReportingNotPossibleException {

        if ("".equals(userId)) {

            log.error("L'utilisateur n'est pas renseigné !");
            throw new ReportingNotPossibleException("L'utilisateur n'est pas renseigné !");

        }

        Reporting reporting = new Reporting();
        LocalDate today = LocalDate.now();
        LocalDate seventhDay = today.minusDays(5);

        List<Meal> meals = iMealService.getAllMealsBetweenTwoDate(seventhDay, today, userId);

        if (meals.isEmpty()) {

            log.error("Pas de repas consommé sur les 7 derniers jours !");
            throw new ReportingNotPossibleException("Pas de repas consommé sur les 7 derniers jours !");

        }

        Optional<Reporting> r = iReportingRepository.findByUserIdAndToday(userId, today);

        if (r.isPresent()) {

            r.get().setNumberMeals(meals.size());
            r.get().setEnergy(0);
            r.get().setCarbohydrates(0);
            r.get().setFat(0);
            r.get().setProteins(0);
            r.get().setSalt(0);
            r.get().setSaturatedFat(0);
            r.get().setSugars(0);

            for (Meal meal : meals) {

                Composition nt = meal.getComposition();
                r.get().setEnergy(r.get().getEnergy() + nt.getEnergy());
                r.get().setEnergyKjUnit(nt.getEnergyKjUnit());
                r.get().setCarbohydrates(r.get().getCarbohydrates() + nt.getCarbohydrates());
                r.get().setCarbohydratesUnit(nt.getCarbohydratesUnit());
                r.get().setFat(r.get().getFat() + nt.getFat());
                r.get().setFatUnit(nt.getFatUnit());
                r.get().setProteins(r.get().getProteins() + nt.getProteins());
                r.get().setProteinsUnit(nt.getProteinsUnit());
                r.get().setSalt(r.get().getSalt() + nt.getSalt());
                r.get().setSaltUnit(nt.getSaltUnit());
                r.get().setSaturatedFat(r.get().getSaturatedFat() + nt.getSaturatedFat());
                r.get().setSaturatedFatUnit(nt.getSaturatedFatUnit());
                r.get().setSugars(r.get().getSugars() + nt.getSugars());
                r.get().setSugarsUnit(nt.getSugarsUnit());
            }

            return iReportingRepository.saveAndFlush(r.get());

        } else {

            reporting.setNumberMeals(meals.size());
            reporting.setSeventhDay(seventhDay);
            reporting.setToday(today);
            reporting.setDate(new Date());
            reporting.setUserId(userId);

            for (Meal meal : meals) {

                Composition nt = meal.getComposition();
                reporting.setEnergy(reporting.getEnergy() + nt.getEnergy());
                reporting.setEnergyKjUnit(nt.getEnergyKjUnit());
                reporting.setCarbohydrates(reporting.getCarbohydrates() + nt.getCarbohydrates());
                reporting.setCarbohydratesUnit(nt.getCarbohydratesUnit());
                reporting.setFat(reporting.getFat() + nt.getFat());
                reporting.setFatUnit(nt.getFatUnit());
                reporting.setProteins(reporting.getProteins() + nt.getProteins());
                reporting.setProteinsUnit(nt.getProteinsUnit());
                reporting.setSalt(reporting.getSalt() + nt.getSalt());
                reporting.setSaltUnit(nt.getSaltUnit());
                reporting.setSaturatedFat(reporting.getSaturatedFat() + nt.getSaturatedFat());
                reporting.setSaturatedFatUnit(nt.getSaturatedFatUnit());
                reporting.setSugars(reporting.getSugars() + nt.getSugars());
                reporting.setSugarsUnit(nt.getSugarsUnit());

            }

            return iReportingRepository.save(reporting);
        }

    }

}
