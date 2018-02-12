package planner;

import org.junit.Before;
import org.junit.Test;
import planner.exceptions.CircuitInRouteException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class RouteBuilderTest {

    private RouteBuilder routeBuilder;

    @Before
    public void setUp() {
        routeBuilder = new RouteBuilder();
    }

    @Test
    public void testSinglePlaceWithoutCheaperPlace() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Collections.singletonList(new PlaceAndCheaperPlace("x", null));

        //When
        String plan = routeBuilder.plan(input);

        //Then
        assertPlan(plan, input);
    }

    @Test
    public void testSinglePlaceWithCheaperPlace() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Collections.singletonList(new PlaceAndCheaperPlace("x", "y"));

        //When
        String plan = routeBuilder.plan(input);

        //Then
        assertPlan(plan, input);
    }

    @Test
    public void testMultiplePlaces() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Arrays.asList(
                new PlaceAndCheaperPlace("u", null),
                new PlaceAndCheaperPlace("v", "w"),
                new PlaceAndCheaperPlace("w", "z"),
                new PlaceAndCheaperPlace("x", "u"),
                new PlaceAndCheaperPlace("y", "v"),
                new PlaceAndCheaperPlace("z", null)
        );

        //When
        String plan = routeBuilder.plan(input);

        //Then
        System.out.println(plan);
        assertPlan(plan, input);
    }

    @Test
    public void testMultiplePlaces_2() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Arrays.asList(
                new PlaceAndCheaperPlace("x", "y"),
                new PlaceAndCheaperPlace("a", "b"),
                new PlaceAndCheaperPlace("y", "b")
        );

        //When
        String plan = routeBuilder.plan(input);

        //Then
        System.out.println(plan);
        assertPlan(plan, input);
    }

    @Test
    public void testMultiplePlacesWithoutCheaperPlace() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Arrays.asList(
                new PlaceAndCheaperPlace("u", null),
                new PlaceAndCheaperPlace("v", null),
                new PlaceAndCheaperPlace("w", null),
                new PlaceAndCheaperPlace("x", null),
                new PlaceAndCheaperPlace("y", null),
                new PlaceAndCheaperPlace("z", null)
        );

        //When
        String plan = routeBuilder.plan(input);

        //Then
        System.out.println(plan);
        assertPlan(plan, input);
    }

    @Test(expected = CircuitInRouteException.class)
    public void testCircuit_1_ShouldFail() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Arrays.asList(
                new PlaceAndCheaperPlace("x", "y"),
                new PlaceAndCheaperPlace("y", "z"),
                new PlaceAndCheaperPlace("z", "x")
        );

        //When
        routeBuilder.plan(input);
    }

    @Test(expected = CircuitInRouteException.class)
    public void testCircuit_2_ShouldFail() throws CircuitInRouteException {
        //Given
        List<PlaceAndCheaperPlace> input = Arrays.asList(
                new PlaceAndCheaperPlace("x", "y"),
                new PlaceAndCheaperPlace("z", "y"),
                new PlaceAndCheaperPlace("y", "a"),
                new PlaceAndCheaperPlace("c", "b"),
                new PlaceAndCheaperPlace("b", "x")

        );

        //When
        routeBuilder.plan(input);
    }

    private void assertPlan(String plan, List<PlaceAndCheaperPlace> input) {
        for (PlaceAndCheaperPlace placeAndCheaperPlace : input) {
            Character placeName = placeAndCheaperPlace.getPlaceName().charAt(0);
            assertTrue(plan.chars().filter(ch -> ch == placeName).count() == 1);

            if (placeAndCheaperPlace.getDependentPlaceName() != null) {
                Character dependentPlaceName = placeAndCheaperPlace.getDependentPlaceName().charAt(0);
                assertTrue(plan.chars().filter(ch -> ch == dependentPlaceName).count() == 1);

                int indexOfPlace = plan.indexOf(placeName);
                int indexOfDependentPlace = plan.indexOf(dependentPlaceName);

                assertTrue(indexOfDependentPlace < indexOfPlace);
            }

        }
    }
}