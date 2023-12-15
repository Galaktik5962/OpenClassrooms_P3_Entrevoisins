package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override

    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavoriteNeighbours() {

        // Create a new list to store the favorite neighbours
        List<Neighbour> favoriteNeighbours = new ArrayList<>();

        // Iterate through all neighbours in the list
        for (Neighbour neighbour : neighbours) {

            // Check if the neighbour is marked as a favorite
            if (neighbour.isFavorite()) {

                // If it is a favorite, add it to the list of favorite neighbours
                favoriteNeighbours.add(neighbour);
            }
        }

        // Return the list of favorite neighbours
        return favoriteNeighbours;
    }

    /**
     * {@inheritDoc}
     * @param favoriteNeighbours
     */
    @Override
    public void setFavoriteNeighbours(List<Neighbour> favoriteNeighbours) {
        for (Neighbour neighbour : neighbours) {

            // Check if the current neighbour is in the list of favorite neighbours
            if (favoriteNeighbours.contains(neighbour)) {

                // Set the 'favorite' property to true for the current neighbour
                neighbour.setFavorite(true);

            } else {

                // Set the 'favorite' property to false for the current neighbour
                neighbour.setFavorite(false);
            }
        }
    }
}

