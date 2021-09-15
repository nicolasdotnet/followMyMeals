package net.nicolasdot.meal_service.services.interfaces;

import net.nicolasdot.meal_service.entity.Reporting;
import net.nicolasdot.meal_service.exceptions.ReportingNotPossibleException;

/**
 *
 * @author nicolasdotnet
 */
public interface IReportingService {

    Reporting reporting(String userId) throws ReportingNotPossibleException;
}
