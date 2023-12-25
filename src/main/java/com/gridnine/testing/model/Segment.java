package com.gridnine.testing.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
/**
 * Bean that represents a flight segment.
 * Класс, представляющий Сегмент перелёта.
 */
public class Segment {

    private final LocalDateTime departureDate;
    private final LocalDateTime arrivalDate;

    public Segment(int id, final LocalDateTime dep, final LocalDateTime arr) {
        departureDate = Objects.requireNonNull(dep);
        arrivalDate = Objects.requireNonNull(arr);
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt =
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        return '[' + departureDate.format(fmt) + " departure" +  '|' + arrivalDate.format(fmt) + " arrival"
                + ']';
    }
}