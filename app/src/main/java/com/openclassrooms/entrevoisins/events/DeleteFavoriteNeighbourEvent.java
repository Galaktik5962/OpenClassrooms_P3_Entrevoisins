package com.openclassrooms.entrevoisins.events;

import com.openclassrooms.entrevoisins.model.Neighbour;

/**
 * Event fired when a user deletes a favorite Neighbour
 */
public class DeleteFavoriteNeighbourEvent {

    /**
     * Favorite neighbour to delete
     */
    public Neighbour neighbour;

    /**
     * Constructor.
     *
     * @param neighbour The favorite neighbour to delete
     */
    public DeleteFavoriteNeighbourEvent(Neighbour neighbour) {
        this.neighbour = neighbour;
    }
}

