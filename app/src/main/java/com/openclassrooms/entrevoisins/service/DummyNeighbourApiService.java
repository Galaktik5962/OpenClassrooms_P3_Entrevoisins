package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    private List<Neighbour> favoriteNeighbours = new ArrayList<>();

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

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void addFavoriteNeighbour(Neighbour neighbour) {
            favoriteNeighbours.add(neighbour);
        }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void removeFavoriteNeighbour(Neighbour neighbour) {
        favoriteNeighbours.remove(neighbour);
    }
}

