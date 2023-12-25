package com.gridnine.testing.builder;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Factory class to get sample list of flights.
 * Класс для создания набора перелётов.
 */
public class FlightBuilder {

    private static int flightId = 0;

    private static int segmentId = 0;

    /**
     * Method to create sample list of flights.
     * Метод для создания набора перелётов.
     */
    public static List<Flight> createFlights() {
        LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
        return Arrays.asList(
                //A normal flight with two hour duration
                // Обычный перелёт продолжительностью два часа
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2)),
                //A normal multi segment flight
                // Обычный перелёт, включающий несколько сегментов
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(5)),
                //A flight departing in the past
                // Перелёт с вылетом в прошлом
                createFlight(threeDaysFromNow.minusDays(6), threeDaysFromNow),
                //A flight which arrives before it's departure
                // Перелёт с прилётом ранее, чем отправление
                createFlight(threeDaysFromNow, threeDaysFromNow.minusHours(6)),
                //A flight with more than two hours ground time
                // Перелёт с прилётом ранее, чем отправление
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(5), threeDaysFromNow.plusHours(6)),
                //Another flight with more than two hours ground time
                //Еще один перелет, где общее время, проведённое на земле, превышает два часа
                createFlight(threeDaysFromNow, threeDaysFromNow.plusHours(2),
                        threeDaysFromNow.plusHours(3), threeDaysFromNow.plusHours(4),
                        threeDaysFromNow.plusHours(6), threeDaysFromNow.plusHours(7)));
    }

    /**
     * Method to create one flight example.
     * Метод для создания одного перелёта.
     */
    public static Flight createFlight(final LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException(
                    "you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segmentId++;
            segments.add(new Segment(segmentId, dates[i], dates[i + 1]));
        }
        flightId++;
        return new Flight(flightId, segments);
    }
}
