package planner;

import planner.exceptions.DuplicatePlaceException;
import planner.exceptions.InvalidInputLineException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class InputParser {

    private static final String VALIDATION_PATTERN = "[a-z] =>( [a-z])?";

    private Pattern pattern = Pattern.compile(VALIDATION_PATTERN);
    private List<PlaceAndCheaperPlace> parsedPlaces;


    public List<PlaceAndCheaperPlace> parseInput(Stream<String> input) throws InvalidInputLineException, DuplicatePlaceException {
        parsedPlaces = new ArrayList<>();

        for (Iterator<String> it = input.iterator(); it.hasNext(); ) {
            String inputLine = it.next();
            validateLine(inputLine);
            parsedPlaces.add(parseLine(inputLine));
        }
        return parsedPlaces;
    }

    private PlaceAndCheaperPlace parseLine(String inputLine) throws DuplicatePlaceException, InvalidInputLineException {
        String[] parts = inputLine.split("=>");
        String placeName = parts[0].trim();
        checkIfPlaceAlreadyParsed(placeName);
        String cheaperPlaceName = null;

        if (parts.length == 2) {
            cheaperPlaceName = parts[1].trim();
            if (cheaperPlaceName.isEmpty() || cheaperPlaceName.equals(placeName)) {
                throw new InvalidInputLineException();
            }
        }

        return new PlaceAndCheaperPlace(placeName, cheaperPlaceName);
    }

    private void checkIfPlaceAlreadyParsed(final String placeName) throws DuplicatePlaceException {
        if (parsedPlaces.stream().anyMatch(place -> placeName.equals(place.getPlaceName()))) {
            throw new DuplicatePlaceException();
        }
    }

    private void validateLine(String inputLine) throws InvalidInputLineException {
        if (!pattern.matcher(inputLine).find()) {
            throw new InvalidInputLineException();
        }
    }
}
