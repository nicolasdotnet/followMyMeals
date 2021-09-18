package net.nicolasdot.meal_service.services.interfaces;

import net.nicolasdot.meal_service.entity.Reporting;
import net.nicolasdot.meal_service.exceptions.ReportingNotPossibleException;

/**
 *
 * @author nicolasdotnet
 */
public interface IReportingService {

    /**
     * method to create a reporting by user id
     *
     * @param userId
     * @return reporting create
     * @throws net.nicolasdot.meal_service.exceptions.ReportingNotPossibleException
     */
    Reporting reporting(String userId) throws ReportingNotPossibleException;
}
