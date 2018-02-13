package planner;

import org.junit.Test;
import planner.exceptions.DuplicatePlaceException;
import planner.exceptions.InvalidInputLineException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class InputParserTest {

    private InputParser inputParser = new InputParser();

    @Test
    public void testSinglePlaceWithoutCheaperPlace() throws Exception {
        //Given
        List<String> input = Collections.singletonList("x =>");

        //When
        List<PlaceAndCheaperPlace> placeAndCheaperPlaces = inputParser.parseInput(input.stream());

        //Then
        assertEquals(1, placeAndCheaperPlaces.size());
        assertEquals("x", placeAndCheaperPlaces.get(0).getPlaceName());
        assertNull(placeAndCheaperPlaces.get(0).getDependentPlaceName());
    }

    @Test
    public void testSinglePlaceWithCheaperInputPlace() throws Exception {
        //Given
        List<String> input = Collections.singletonList("x => y");

        //When
        List<PlaceAndCheaperPlace> placeAndCheaperPlaces = inputParser.parseInput(input.stream());

        //Then
        assertEquals(1, placeAndCheaperPlaces.size());
        assertEquals("x", placeAndCheaperPlaces.get(0).getPlaceName());
        assertEquals("y",placeAndCheaperPlaces.get(0).getDependentPlaceName());
    }

    @Test
    public void testMultiplePlaces() throws Exception {
        //Given
        List<String> input = Arrays.asList("x => y","y =>");

        //When
        List<PlaceAndCheaperPlace> placeAndCheaperPlaces = inputParser.parseInput(input.stream());

        //Then
        assertEquals(2, placeAndCheaperPlaces.size());
        assertEquals("x", placeAndCheaperPlaces.get(0).getPlaceName());
        assertEquals("y",placeAndCheaperPlaces.get(0).getDependentPlaceName());
        assertEquals("y", placeAndCheaperPlaces.get(1).getPlaceName());
        assertNull(placeAndCheaperPlaces.get(1).getDependentPlaceName());
    }

    @Test(expected = InvalidInputLineException.class)
    public void testBlankLineShouldThrowException() throws Exception {
        //Given
        List<String> input = Collections.singletonList("");

        //When
        inputParser.parseInput(input.stream());

    }

    @Test(expected = InvalidInputLineException.class)
    public void testEmptyPlaceShouldThrowException() throws Exception {
        //Given
        List<String> input = Collections.singletonList("=> y");

        //When
        inputParser.parseInput(input.stream());

    }

    @Test(expected = InvalidInputLineException.class)
    public void testEmptyCheaperPlaceShouldThrowException() throws Exception {
        //Given
        List<String> input = Collections.singletonList("x => ");

        //When
        inputParser.parseInput(input.stream());

    }

    @Test(expected = DuplicatePlaceException.class)
    public void testDuplicatePlacesShouldThrowException() throws Exception {
        //Given
        List<String> input = Arrays.asList("x =>","x => y");

        //When
        inputParser.parseInput(input.stream());

    }
}