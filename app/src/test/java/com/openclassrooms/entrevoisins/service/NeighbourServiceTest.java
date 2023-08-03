package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private DummyNeighbourApiService neighbourApiService;

    @Before
    public void setup() {

        service = DI.getNewInstanceApiService();
        neighbourApiService = new DummyNeighbourApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void createNeighbourWithSuccess() {

        // Create a neighbor for the test.
        Neighbour neighbour = new Neighbour(1, "John Doe", "http://example.com/avatar.jpg", "Some address", "0123456789", "About John Doe");

        // Calling the createNeighbour function.
        service.createNeighbour(neighbour);

        // Verification that the neighbor was added successfully.
        assertTrue(service.getNeighbours().contains(neighbour));
    }

    @Test
    public void testSetFavoriteNeighbours() {
        List<Neighbour> neighbours = neighbourApiService.getNeighbours();

        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        favoriteNeighbours.add(neighbours.get(0));
        favoriteNeighbours.add(neighbours.get(2));

        neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);

        // Preferred Neighbors Update Check.
        for (Neighbour neighbour : neighbours) {
            if (favoriteNeighbours.contains(neighbour)) {
                Assert.assertTrue(neighbour.isFavorite());
            } else {
                Assert.assertFalse(neighbour.isFavorite());
            }
        }

        // Checking that the favorites returned by getFavoriteNeighbours are correct.
        List<Neighbour> returnedFavoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
        Assert.assertEquals(favoriteNeighbours.size(), returnedFavoriteNeighbours.size());
        Assert.assertTrue(returnedFavoriteNeighbours.containsAll(favoriteNeighbours));
    }

}
