package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.List;

/**
 * Neighbour API client
 */
public interface NeighbourApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Neighbour> getNeighbours();

    /**
     * Deletes a neighbour
     * @param neighbour
     */
    void deleteNeighbour(Neighbour neighbour);

    /**
     * Create a neighbour
     * @param neighbour
     */

    void createNeighbour(Neighbour neighbour);

   /** Method that returns a list of neighbors marked as favorites */
    List<Neighbour> getFavoriteNeighbours();

    /**
     * Updates the favorite status of a neighbor.
     * @param favoriteNeighbours The neighbor for which to update the favorite status.
     */
    void setFavoriteNeighbours(List<Neighbour> favoriteNeighbours);

    /**
     * Adds a neighbor to the list of favorite neighbors.
     * @param neighbour The neighbor to add to the list of favorite neighbors.
     */
    void addFavoriteNeighbour(Neighbour neighbour);

    /**
     * Removes a neighbor from the list of favorite neighbors.
     * @param neighbour The neighbor to remove from the list of favorite neighbors.
     */
    void removeFavoriteNeighbour(Neighbour neighbour);
}
