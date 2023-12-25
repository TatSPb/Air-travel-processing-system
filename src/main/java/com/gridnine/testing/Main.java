package com.gridnine.testing;

import com.gridnine.testing.filter.Filterable;
import com.gridnine.testing.filter.impl.FilterImpl;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.builder.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FlightBuilder flightBuilder = new FlightBuilder();
        List<Flight> flightList = flightBuilder.createFlights();
        Filterable segmentFilterable = new FilterImpl();
        segmentFilterable.showAllFlights(flightList);
        segmentFilterable.getFlightsWithDepartureBeforeNow(flightList);
        segmentFilterable.getFlightsWithArrivalBeforeDeparture(flightList);
        segmentFilterable.getFlightsWithGroundTimeMoreThenTwoHours(flightList);
    }
}
