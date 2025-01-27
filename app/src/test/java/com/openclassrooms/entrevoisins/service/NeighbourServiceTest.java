package com.openclassrooms.entrevoisins.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

/**
 * Unit tests for the NeighbourService class.
 * These tests cover various functionalities of the NeighbourService, including
 * retrieving neighbours, deleting neighbours, creating new neighbours, and setting
 * and retrieving favorite neighbours.
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;
    private DummyNeighbourApiService neighbourApiService;

    /**
     * Sets up the test environment by initializing instances of NeighbourApiService
     * and DummyNeighbourApiService before each test method.
     */
    @Before
    public void setup() {

        service = DI.getNewInstanceApiService();
        neighbourApiService = new DummyNeighbourApiService();
    }

    /**
     * Tests the successful retrieval of neighbours from the NeighbourService.
     */
    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedNeighbours.toArray()));
    }

    /**
     * Tests the successful deletion of a neighbour from the NeighbourService.
     */
    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * Tests the successful creation of a new neighbour using the NeighbourService.
     */
    @Test
    public void createNeighbourWithSuccess() {

        // Create a neighbour for the test
        Neighbour neighbour = new Neighbour(1, "John Doe", "http://example.com/avatar.jpg", "Some address", "0123456789", "About John Doe");

        // Calling the createNeighbour function
        service.createNeighbour(neighbour);

        // Verification that the neighbour was added successfully
        assertTrue(service.getNeighbours().contains(neighbour));
    }

    /**
     * Tests the correct functionality of retrieving favorite neighbours using the
     * NeighbourApiService.
     */
    @Test
    public void testGetFavoriteNeighbours() {
        List<Neighbour> neighbours = neighbourApiService.getNeighbours();

        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        favoriteNeighbours.add(neighbours.get(0));
        favoriteNeighbours.add(neighbours.get(2));

        neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);

        // Verify that neighbours are correctly marked as favorites after calling setFavoriteNeighbours
        for (Neighbour neighbour : neighbours) {
            if (favoriteNeighbours.contains(neighbour)) {
                Assert.assertTrue(neighbour.isFavorite());
                neighbourApiService.addFavoriteNeighbour(neighbour);
            } else {
                Assert.assertFalse(neighbour.isFavorite());
            }
        }

        // Checking that the favorites returned by getFavoriteNeighbours are correct
        List<Neighbour> returnedFavoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
        Assert.assertEquals(favoriteNeighbours.size(), returnedFavoriteNeighbours.size());
        Assert.assertTrue(returnedFavoriteNeighbours.containsAll(favoriteNeighbours));
    }

    /**
     * Tests the correct functionality of removing a favorite neighbour using the
     * NeighbourApiService.
     */
    @Test
    public void testRemoveFavoriteNeighbour() {
        List<Neighbour> neighbours = neighbourApiService.getNeighbours();

        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        favoriteNeighbours.add(neighbours.get(0));
        favoriteNeighbours.add(neighbours.get(2));

        neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);

        // Verify that neighbours are correctly marked as favorites after calling setFavoriteNeighbours
        for (Neighbour neighbour : neighbours) {
            if (favoriteNeighbours.contains(neighbour)) {
                Assert.assertTrue(neighbour.isFavorite());
                neighbourApiService.addFavoriteNeighbour(neighbour);
            } else {
                Assert.assertFalse(neighbour.isFavorite());
            }
        }

        // Checking that the favorites returned by getFavoriteNeighbours are correct
        List<Neighbour> returnedFavoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
        Assert.assertEquals(favoriteNeighbours.size(), returnedFavoriteNeighbours.size());
        Assert.assertTrue(returnedFavoriteNeighbours.containsAll(favoriteNeighbours));

        // Removing favorites neighbours
        neighbourApiService.removeFavoriteNeighbour(favoriteNeighbours.get(0));
        neighbourApiService.removeFavoriteNeighbour(favoriteNeighbours.get(1));

        // Checking that the favorites are deleted correctly
        assertTrue(returnedFavoriteNeighbours.isEmpty());
        Assert.assertFalse(returnedFavoriteNeighbours.containsAll(favoriteNeighbours));
    }

    /**
     * Tests the correct functionality of adding a favorite neighbour using the
     * NeighbourApiService.
     */
    @Test
    public void testAddFavoriteNeighbour() {
        List<Neighbour> neighbours = neighbourApiService.getNeighbours();

        List<Neighbour> favoriteNeighbours = new ArrayList<>();
        favoriteNeighbours.add(neighbours.get(0));
        favoriteNeighbours.add(neighbours.get(2));

        neighbourApiService.setFavoriteNeighbours(favoriteNeighbours);

        // Verify that neighbours are correctly marked as favorites after calling setFavoriteNeighbours
        for (Neighbour neighbour : neighbours) {
            if (favoriteNeighbours.contains(neighbour)) {
                Assert.assertTrue(neighbour.isFavorite());
            } else {
                Assert.assertFalse(neighbour.isFavorite());
            }
        }

        assertTrue(neighbourApiService.getFavoriteNeighbours().isEmpty());

        // Adding favorites neighbours
        neighbourApiService.addFavoriteNeighbour(neighbours.get(0));
        neighbourApiService.addFavoriteNeighbour(neighbours.get(2));

        // Checking that the favorites are added correctly
        List<Neighbour> returnedFavoriteNeighbours = neighbourApiService.getFavoriteNeighbours();
        Assert.assertEquals(favoriteNeighbours.size(), returnedFavoriteNeighbours.size());
        Assert.assertTrue(returnedFavoriteNeighbours.containsAll(favoriteNeighbours));
    }
}