package planner;

import java.util.List;

public class HolidayPlanner {

    private final InputParser inputParser;
    private final RouteBuilder routePlanner;

    public HolidayPlanner() {
        inputParser = new InputParser();
        routePlanner = new RouteBuilder();
    }

    public String planRoute(List<String> input) throws Exception {

        List<PlaceAndCheaperPlace> places = inputParser.parseInput(input);

        return routePlanner.plan(places);
    }

}
