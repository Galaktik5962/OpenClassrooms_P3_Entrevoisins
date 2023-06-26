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

    @Override
    public List<Neighbour> getFavoriteNeighbours() {
        return favoriteNeighbours;
    }

    @Override
    public void setFavoriteNeighbours(List<Neighbour> favoriteNeighbours) {
        this.favoriteNeighbours = favoriteNeighbours;
    }

    @Override
    public void addFavoriteNeighbour(Neighbour neighbour) {
        favoriteNeighbours.add(neighbour);
    }

    @Override
    public void removeFavoriteNeighbour(Neighbour neighbour) {
        favoriteNeighbours.remove(neighbour);
    }




    } // implémenter les deux méthodes getfavorite (boolean pour filtrer) et setfavorite (boolean dans nieghbour)
    //logique de gestion de favori et ensuite renvoyer la liste de favori

