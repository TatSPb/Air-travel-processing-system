package com.gridnine.testing.filter;

import com.gridnine.testing.model.Flight;

import java.util.List;
import java.util.Set;

/**
 * An interface for flight filtering.
 * Интерфейс для фильтрации полетов.
 */
public interface Filterable {
    /**
     * A set of normal flights with one or more segments and with no ground time between segments with more then 2 hours
     * duration.
     * Набор обычных перелётов, состоящих из 1 и более сегментов, и у которых время, проведенное между сегментами
     * на земле не превышает 2 часа.
     */
    void showAllFlights(List<Flight> flights);

    /**
     * A set of flights for exception due to departure in the past (departure is earlier than the current point of time).
     * Набор перелётов для исключения, у которых дата вылета раньше текущего момента времени.
     */
    Set<Flight> getFlightsWithDepartureBeforeNow(List<Flight> flights);

    /**
     * A set of flights for exception due to an arrival date earlier than the departure date.
     * Набор перелётов для исключения, у которых дата прилёта раньше даты вылета хотя бы в одном сегменте.
     */
    Set<Flight> getFlightsWithArrivalBeforeDeparture(List<Flight> flights);

    /**
     * A set of flights for exception due to more than two hours ground time.
     * Набор перелётов для исключения, у которых общее время, проведённое на земле между сегментами, превышает два часа.
     */
    Set<Flight> getFlightsWithGroundTimeMoreThenTwoHours(List<Flight> flights);
}
