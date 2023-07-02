package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
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

    List<Neighbour> getFavoriteNeighbours();

    void setFavoriteNeighbours(List<Neighbour> favoriteNeighbours);





    //méthode pour récupérer mes favoris, getfavoriteneighbours + setfavoriteneighbour (ajouter ou enlever l'état de favori)
}
