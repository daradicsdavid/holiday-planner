package planner;

import planner.exceptions.CircuitInRouteException;
import planner.exceptions.DuplicatePlaceException;
import planner.exceptions.InvalidInputLineException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class HolidayPlanner {

    private final InputParser inputParser;
    private final RouteBuilder routeBuilder;

    public HolidayPlanner() {
        inputParser = new InputParser();
        routeBuilder = new RouteBuilder();
    }

    public String planRoute(Stream<String> input) throws DuplicatePlaceException, InvalidInputLineException, CircuitInRouteException {

        List<PlaceAndCheaperPlace> places = inputParser.parseInput(input);

        return routeBuilder.plan(places);
    }

    public String planRouteFromFile(String fileName) throws IOException, DuplicatePlaceException, InvalidInputLineException, CircuitInRouteException {
        return planRoute(Files.lines(Paths.get(fileName)));
    }

}
