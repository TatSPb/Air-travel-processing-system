package com.gridnine.testing.filter.impl;

import com.gridnine.testing.filter.Filterable;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterImpl implements Filterable {

    /**
     * Method to filter and console output a set of normal flights with one or more segments and with no ground time
     * between segments with more then 2 hours duration. Clarification: in this case, the minute of departure is NOT
     * considered time spent on the ground, for example, provided Departure time: 2023-12-27T03:42 & Arrival time:
     * 2023-12-27T01:42 the flight is included in this set.

     * Метод для фильтра и вывода в консоль набора обычных перелётов,состоящих из 1 и более сегментов, и у которых
     * время, проведенное между сегментами на земле не превышает 2 часа. Уточнение: при этом минута вылета НЕ считается
     * временем, проведенным на земле, например, при условии Departure time: 2023-12-27T03:42 & Arrival time:
     * 2023-12-27T01:42 перелёт включается в данный набор.
     */
    @Override
    public void showAllFlights(List<Flight> flights) {
        System.out.println("******************** The whole set of flights: *********************");
        for (Flight flight : flights){
            System.out.println("Flight Number :  " + flight.getId());
            for (int i = 0; i < flight.getSegments().size() ; i++) {
                System.out.println("Segment       : " + flight.getSegments().get(i));
            }
            System.out.println("---------------------------------------------------------------------");
        }
        System.out.println("******************** Exceptions: *********************");
    }
    /**
     * Method to filter and console output a set of flights for exception with departure in the past
     * (departure is earlier than the current point of time).
     * Метод для фильтра и вывода в консоль набора перелётов, у которых дата вылета раньше текущего момента времени.
     */
    @Override
    public Set<Flight> getFlightsWithDepartureBeforeNow(List<Flight> flights) {
        LocalDateTime localDateTime = LocalDateTime.now();
        Set<Flight> setOfFlights = new HashSet<>();
        List<Segment> listOfFlights = new ArrayList<>();
        for (Flight flight : flights){
            listOfFlights.addAll(flight.getSegments());
            while (listOfFlights.size() > 0){
                LocalDateTime departure = listOfFlights.get(0).getDepartureDate();
                LocalDateTime arrival = listOfFlights.remove(0).getArrivalDate();
                if (departure.isBefore(localDateTime)){
                    System.out.println( "Flight Number  : " + flight.getId() + " - the flight departing in the past");
                    showInformation(arrival, departure);
                    setOfFlights.add(flight);
                }
            }
        }
        return setOfFlights;
    }
    /**
     * Method to filter and console output a set of flights with an arrival date earlier than the departure date.
     * Метод для фильтра и вывода в консоль набора перелётов, у которых дата/время прилета раньше чем дата/время вылета.
     */
    @Override
    public Set<Flight> getFlightsWithArrivalBeforeDeparture(List<Flight> flights) {
        Set<Flight> setOfFlights = new HashSet<>();
        List<Segment> listOfFlights = new ArrayList<>();
        for (Flight flight : flights){
            listOfFlights.addAll(flight.getSegments());
            while (listOfFlights.size() > 0){
                LocalDateTime departure = listOfFlights.get(0).getDepartureDate();
                LocalDateTime arrival = listOfFlights.remove(0).getArrivalDate();
                if (arrival.isBefore(departure)){
                    System.out.println( "Flight Number  : " + flight.getId() +
                            " - the flight that departs before it arrives");
                    showInformation(arrival, departure);
                    setOfFlights.add(flight);
                }
            }
        }
        return setOfFlights;
    }
    /**
     * Method to filter and console output a set of flights with more than two hours ground time. Clarification:
     * in this case, the minute of departure is NOT considered time spent on the ground, for example,
     * provided Arrival time: 2023-12-27T01:42 & Departure time: 2023-12-27T03:42 the flight is NOT included in this set.
     * Метод для фильтра и вывода в консоль набора перелётов, у которых общее время, проведённое на земле
     * между сегментами, превышает два часа. Уточнение: при этом минута вылета НЕ считается временем,
     * проведенным на земле, например, при условии Departure time: 2023-12-27T03:42 & Arrival time: 2023-12-27T01:42
     * перелёт НЕ включается в данный набор.
     */
    @Override
    public Set<Flight> getFlightsWithGroundTimeMoreThenTwoHours(List<Flight> flights) {
        Set<Flight> setOfFlights = new HashSet<>();
        List<Segment> listOfFlights = new ArrayList<>();
        for (Flight flight : flights ) {
            int countHours = 0;
            listOfFlights.addAll(flight.getSegments());
            if (listOfFlights.size() >= 2) {
                while (listOfFlights.size() >= 2) {
                    LocalDateTime arrival = listOfFlights.get(0).getArrivalDate();
                    LocalDateTime departure = listOfFlights.get(0).getDepartureDate();
                    countHours = countHours + durationOnTheGround(arrival,departure);
                    if(countHours > 2) {
                        System.out.println( "Flight Number  : " + flight.getId() +
                                " - the flight with more than two hours ground time");
                        showInformation(arrival, departure);
                        setOfFlights.add(flight);
                    }
                }
            }
            listOfFlights.clear();
        }
        return setOfFlights;
    }

    /**
     * Util method to console output format.
     * Утилитарный метод для форматирования вывода в консоль.
     */
    private void showInformation(LocalDateTime arrival, LocalDateTime departure) {
        System.out.println("Departure time : " + departure.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        System.out.println("Arrival time   : " + arrival.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        System.out.println("---------------------------------------------------------------------");
    }

    /**
     * Util method to calculate of on the ground duration.
     * Утилитарный метод для расчета продолжительности пребывания на земле.
     */
    public int durationOnTheGround(LocalDateTime arrival, LocalDateTime departure) {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return (int) ChronoUnit.HOURS.between(arrival, departure);
    }
}
